package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskUpdateRequestDTO {

    @NotNull(message = "Status is required")
    private TaskStatus status;
}
