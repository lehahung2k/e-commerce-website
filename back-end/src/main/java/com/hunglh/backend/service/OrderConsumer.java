package com.hunglh.backend.service;

import com.hunglh.backend.config.RabbitMQConfig;
import com.hunglh.backend.entities.OrderMain;
import com.hunglh.backend.service.ProcessingTaskService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Consumer xử lý messages từ RabbitMQ queue.
 *
 * Flow:
 * 1. Nhận OrderMain từ queue
 * 2. INSERT record vào bảng processing_task (để KEDA có thể track)
 * 3. Gửi email xác nhận đơn hàng cho buyer
 * 4. DELETE record khi hoàn thành
 */
@Service
public class OrderConsumer {

    @Autowired
    private ProcessingTaskService processingTaskService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${HOSTNAME:unknown}")
    private String podName;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @RabbitListener(queues = RabbitMQConfig.ORDERS_QUEUE)
    public void processOrder(OrderMain order) {
        String taskId = UUID.randomUUID().toString();
        Long taskDbId = null;

        try {
            // 1. Đánh dấu bắt đầu xử lý (cho KEDA tracking)
            taskDbId = processingTaskService.startTask(taskId, "ORDER", podName);
            System.out.println("[" + podName + "] Processing order #" + order.getOrderId() + " for " + order.getBuyerEmail());

            // 2. Gửi email xác nhận đơn hàng
            if (order.getBuyerEmail() != null) {
                sendConfirmationEmail(order);
            }

            System.out.println("[" + podName + "] Completed order #" + order.getOrderId());

        } catch (Exception e) {
            System.err.println("[" + podName + "] Error processing order #" + order.getOrderId() + " - " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 3. Xóa task khỏi tracking
            if (taskDbId != null) {
                try {
                    processingTaskService.completeTask(taskDbId);
                } catch (Exception e) {
                    System.err.println("[" + podName + "] Failed to remove task: " + taskId);
                }
            }
        }
    }

    private void sendConfirmationEmail(OrderMain order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(order.getBuyerEmail());
            message.setSubject("Xác nhận đơn hàng #" + order.getOrderId());
            message.setText(
                    "Xin chào " + order.getBuyerName() + ",\n\n"
                    + "Đơn hàng #" + order.getOrderId() + " của bạn đã được xác nhận.\n\n"
                    + "Thông tin giao hàng:\n"
                    + "  - Địa chỉ: " + order.getBuyerAddress() + "\n"
                    + "  - Số điện thoại: " + order.getBuyerPhone() + "\n"
                    + "  - Tổng tiền: " + order.getOrderAmount() + " VNĐ\n\n"
                    + "Cảm ơn bạn đã mua sắm tại cửa hàng!\n"
            );
            mailSender.send(message);
            System.out.println("[" + podName + "] Confirmation email sent to: " + order.getBuyerEmail());
        } catch (Exception e) {
            // Không throw exception để không ảnh hưởng tới luồng chính
            System.err.println("[" + podName + "] Failed to send email: " + e.getMessage());
        }
    }
}
