package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.exceptions.CustomException;
import com.example.projectexecutionplatform.models.dtos.*;
import com.example.projectexecutionplatform.models.entities.Project;
import com.example.projectexecutionplatform.models.entities.Sprint;
import com.example.projectexecutionplatform.models.entities.Tasks;
import com.example.projectexecutionplatform.models.entities.Users;
import com.example.projectexecutionplatform.models.enums.TaskStatus;
import com.example.projectexecutionplatform.repositories.ProjectRepository;
import com.example.projectexecutionplatform.repositories.SprintRepository;
import com.example.projectexecutionplatform.repositories.TaskRepository;
import com.example.projectexecutionplatform.repositories.UserRepository;
import com.example.projectexecutionplatform.utils.TaskIdGenerator;
import com.example.projectexecutionplatform.utils.UserIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl {
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private SprintRepository sprintRepository;
//
//    @Autowired
//    private ProjectServiceImpl projectService;
//
//
//    public TaskResponseDTO assignTask(Long projectId, Long taskId, TaskAssignDTO dto) {
//
//        // validate project/task/user/workload
//        validationService.validateAssignment(projectId, taskId, dto.getUserId());
//
//        Tasks task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));
//
//        Users user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
//
//        // set assignment and status
//        task.setAssignedTo(user);
//        task.setStatus(TaskStatus.ASSIGNED);
//
//        Tasks saved = taskRepository.save(task);
//        return mapToDto(saved);
//    }
//
//
//    public TaskResponseDTO updateTaskStatus(Long taskId, TaskStatusUpdateDTO dto) {
//        Tasks task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));
//
//        TaskStatus current = task.getStatus();
//        TaskStatus next = dto.getStatus();
//
//        if (!validationService.isValidTransition(current, next)) {
//            throw new CustomException("Invalid task status transition: " + current + " -> " + next, HttpStatus.BAD_REQUEST);
//        }
//
//        // if transition would make workload exceed (e.g., ASSIGNED -> IN_PROGRESS doesn't change workload),
//        // we only need to check when assigning (done above). But optionally validate here if needed.
//
//        task.setStatus(next);
//        Tasks saved = taskRepository.save(task);
//        return mapToDto(saved);
//    }
//
//
//
//    private TaskResponseDTO mapToDto(Tasks task) {
//        return TaskResponseDTO.builder()
//                .taskId(task.getTaskId())
//                .title(task.getTitle())
//                .description(task.getDescription())
//                .status(task.getStatus())
//                .priority(task.getPriority())
//                .storyPoints(task.getStoryPoints())
//                .projectId(task.getProject() != null ? task.getProject().getId() : null)
//                .sprintId(task.getSprint() != null ? task.getSprint().getId() : null)
//                .assignedToId(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null)
//                .assignedToName(task.getAssignedTo() != null ? task.getAssignedTo().getName() : null)
//                .build();
//    }




    private void validateStatusTransition(TaskStatus oldStatus, TaskStatus newStatus) {

        Map<TaskStatus, List<TaskStatus>> allowed = Map.of(
                TaskStatus.CREATED, List.of(TaskStatus.ASSIGNED),
                TaskStatus.ASSIGNED, List.of(TaskStatus.IN_PROGRESS),
                TaskStatus.IN_PROGRESS, List.of(TaskStatus.BLOCKED, TaskStatus.COMPLETED),
                TaskStatus.BLOCKED, List.of(TaskStatus.IN_PROGRESS)
        );

        if (!allowed.getOrDefault(oldStatus, List.of()).contains(newStatus)) {
            throw new CustomException(
                    "Invalid status transition: " + oldStatus + " → " + newStatus,
                    HttpStatus.BAD_REQUEST
            );
        }


    }
}