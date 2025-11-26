package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.TaskPriority;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Story points are required")
    @Min(value = 1,message = "Story points must be minimum 1")
    @Max(value = 13, message = "Story points must be maximum 13")
    private Integer storyPoints;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Project ID is required")
    private Long sprintId;

    @NotNull(message = "Assigned User ID is required")
    private Long assignedTo;

    @NotNull(message = "Creator ID is required")
    private Long createdBy;

}
