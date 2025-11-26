package com.hunglh.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity để tracking các tasks đang được xử lý bởi consumers.
 * KEDA sẽ query COUNT(*) từ bảng này để biết có bao nhiêu tasks đang chạy.
 * 
 * Flow:
 * 1. Consumer nhận message từ queue
 * 2. Insert record vào bảng này (status = PROCESSING)
 * 3. Xử lý message (simulate heavy work)
 * 4. Delete record khi xong
 * 
 * Nếu có nhiều records trong bảng này → Hệ thống đang bận → KEDA scale up
 * Nếu ít records → Hệ thống nhàn rỗi → KEDA có thể scale down
 */
@Entity
@Table(name = "processing_task")
@Data
@NoArgsConstructor
public class ProcessingTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id", nullable = false)
    private String taskId;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "pod_name")
    private String podName;

    @Column(name = "status")
    private String status = "PROCESSING";

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ProcessingTask(String taskId, String taskType, String podName) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.podName = podName;
        this.status = "PROCESSING";
    }
}
