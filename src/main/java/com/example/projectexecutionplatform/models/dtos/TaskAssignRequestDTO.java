package com.example.projectexecutionplatform.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskAssignRequestDTO {

    @NotNull(message = "Task ID is required")
    private String taskId;

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Project ID is required")
    private String projectId;
}
