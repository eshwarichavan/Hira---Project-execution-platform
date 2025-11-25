package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStatusUpdateDTO {

    private TaskStatus status;
}
