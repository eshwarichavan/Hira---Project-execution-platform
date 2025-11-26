package com.example.projectexecutionplatform.models.dtos;


import com.example.projectexecutionplatform.models.enums.TaskPriority;
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
public class TaskResponseDTO {

    private String taskId;
    private String title;
    private String description;
    private Integer storyPoints;

    private String projectId;
    private String sprintId;
    private String assignedTo;

    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;


}
