package com.hunglh.backend.service;

import com.hunglh.backend.config.RabbitMQConfig;
import com.hunglh.backend.entities.ProcessingTask;
import com.hunglh.backend.repositories.ProcessingTaskRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Consumer xử lý messages từ RabbitMQ queue.
 * 
 * Flow để KEDA có thể track DB load:
 * 1. Nhận message từ queue
 * 2. INSERT record vào bảng processing_task (đánh dấu đang xử lý) - COMMIT NGAY
 * 3. Simulate heavy processing (sleep 5s)
 * 4. DELETE record khi hoàn thành - COMMIT NGAY
 * 
 * KEDA query: SELECT COUNT(*) FROM processing_task WHERE status = 'PROCESSING'
 * - Nếu count cao → nhiều tasks đang chạy → có thể cần scale up
 * - Nếu count thấp → ít tasks → có thể scale down
 * 
 * LƯU Ý: Sử dụng REQUIRES_NEW để mỗi thao tác DB được commit ngay lập tức,
 * cho phép KEDA query thấy được số tasks đang xử lý trong thời gian thực.
 */
@Service
public class OrderConsumer {

    @Autowired
    private ProcessingTaskService processingTaskService;

    @Value("${HOSTNAME:unknown}")
    private String podName;

    @RabbitListener(queues = RabbitMQConfig.ORDERS_QUEUE)
    public void processOrder(Object order) {
        String taskId = UUID.randomUUID().toString();
        Long taskDbId = null;
        
        try {
            // 1. Insert record - đánh dấu bắt đầu xử lý (commit ngay lập tức)
            taskDbId = processingTaskService.startTask(taskId, "ORDER", podName);
            System.out.println("[" + podName + "] Started processing task: " + taskId + " (DB ID: " + taskDbId + ")");

            // 2. Simulate heavy processing (5 giây)
            // Trong thực tế đây là logic xử lý order, gọi API, etc.
            Thread.sleep(5000);

            System.out.println("[" + podName + "] Completed task: " + taskId + ", order: " + order);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[" + podName + "] Task interrupted: " + taskId);
        } catch (Exception e) {
            System.err.println("[" + podName + "] Error processing task: " + taskId + " - " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 3. Delete record - đánh dấu hoàn thành (commit ngay lập tức)
            if (taskDbId != null) {
                try {
                    processingTaskService.completeTask(taskDbId);
                    System.out.println("[" + podName + "] Removed task from tracking: " + taskId);
                } catch (Exception e) {
                    System.err.println("[" + podName + "] Failed to remove task: " + taskId + " - " + e.getMessage());
                }
            }
        }
    }
}
