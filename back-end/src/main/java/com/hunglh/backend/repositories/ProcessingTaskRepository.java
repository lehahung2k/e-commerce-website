package com.hunglh.backend.repositories;

import com.hunglh.backend.entities.ProcessingTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingTaskRepository extends JpaRepository<ProcessingTask, Long> {
    
    /**
     * Đếm số tasks đang được xử lý
     * KEDA sẽ dùng query tương tự để scale
     */
    @Query("SELECT COUNT(p) FROM ProcessingTask p WHERE p.status = 'PROCESSING'")
    long countProcessingTasks();
    
    /**
     * Xóa task theo taskId
     */
    void deleteByTaskId(String taskId);
}
