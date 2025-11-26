package com.hunglh.backend.service;

import com.hunglh.backend.entities.ProcessingTask;
import com.hunglh.backend.repositories.ProcessingTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service để quản lý ProcessingTask với REQUIRES_NEW transaction.
 * 
 * Mỗi method sử dụng REQUIRES_NEW propagation để đảm bảo:
 * - INSERT được commit NGAY LẬP TỨC (không đợi method gọi hoàn thành)
 * - DELETE được commit NGAY LẬP TỨC
 * 
 * Điều này cho phép KEDA query thấy được số tasks đang xử lý trong thời gian thực,
 * thay vì phải đợi đến khi toàn bộ method processOrder hoàn thành.
 */
@Service
public class ProcessingTaskService {

    @Autowired
    private ProcessingTaskRepository processingTaskRepository;

    /**
     * Tạo task mới và commit ngay lập tức.
     * 
     * @param taskId unique task ID
     * @param taskType loại task (ORDER, PAYMENT, etc.)
     * @param podName tên pod đang xử lý
     * @return database ID của task vừa tạo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long startTask(String taskId, String taskType, String podName) {
        ProcessingTask task = new ProcessingTask(taskId, taskType, podName);
        task = processingTaskRepository.save(task);
        // Log để debug
        System.out.println("[DB] Inserted processing_task with ID: " + task.getId() + ", taskId: " + taskId);
        return task.getId();
    }

    /**
     * Xóa task và commit ngay lập tức.
     * 
     * @param taskDbId database ID của task cần xóa
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeTask(Long taskDbId) {
        processingTaskRepository.deleteById(taskDbId);
        System.out.println("[DB] Deleted processing_task with ID: " + taskDbId);
    }

    /**
     * Đếm số tasks đang xử lý.
     * 
     * @return số lượng tasks có status = PROCESSING
     */
    @Transactional(readOnly = true)
    public long countProcessingTasks() {
        return processingTaskRepository.countProcessingTasks();
    }
}
