package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.models.dtos.TaskAssignDTO;
import com.example.projectexecutionplatform.models.dtos.TaskResponseDTO;
import com.example.projectexecutionplatform.models.dtos.TaskStatusUpdateDTO;

public interface TaskService {

    TaskResponseDTO assignTask(Long projectId, Long taskId, TaskAssignDTO dto);
    TaskResponseDTO updateTaskStatus(Long taskId, TaskStatusUpdateDTO dto);
}
