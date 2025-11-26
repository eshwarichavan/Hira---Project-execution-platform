package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.models.dtos.TaskCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.TaskResponseDTO;
import com.example.projectexecutionplatform.models.dtos.TaskUpdateRequestDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskCreateRequestDTO dto);

    TaskResponseDTO updateStatus(String taskId, TaskUpdateRequestDTO dto);

    List<TaskResponseDTO> getTasksByProject(String projectId);

    List<TaskResponseDTO> getTasksByUser(Long userId);


}
