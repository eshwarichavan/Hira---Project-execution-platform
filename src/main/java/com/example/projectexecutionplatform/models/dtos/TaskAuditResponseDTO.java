package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskAuditResponseDTO {

    private String taskId;
    private TaskStatus oldStatus;
    private TaskStatus newStatus;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
