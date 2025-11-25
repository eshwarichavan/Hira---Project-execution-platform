package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequestDTO {

    private String title;
    private String description;
    private TaskPriority priority;
    private Integer storyPoints;
    private Long projectId;
    private Long sprintId;
    private Long assignedTo;
}
