package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.exceptions.CustomException;
import com.example.projectexecutionplatform.models.dtos.TaskAssignRequestDTO;
import com.example.projectexecutionplatform.models.dtos.TaskCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.TaskResponseDTO;
import com.example.projectexecutionplatform.models.dtos.TaskStatusUpdateDTO;
import com.example.projectexecutionplatform.models.entities.Project;
import com.example.projectexecutionplatform.models.entities.Sprint;
import com.example.projectexecutionplatform.models.entities.Tasks;
import com.example.projectexecutionplatform.models.entities.Users;
import com.example.projectexecutionplatform.models.enums.TaskPriority;
import com.example.projectexecutionplatform.models.enums.TaskStatus;
import com.example.projectexecutionplatform.repositories.ProjectRepository;
import com.example.projectexecutionplatform.repositories.SprintRepository;
import com.example.projectexecutionplatform.repositories.TaskRepository;
import com.example.projectexecutionplatform.repositories.UserRepository;
import com.example.projectexecutionplatform.specification.TaskSpecification;
import com.example.projectexecutionplatform.utils.TaskIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SprintRepository sprintRepository;


    // create task :
    public TaskResponseDTO createTask(TaskCreateRequestDTO dto){

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));

        Users creator = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(() -> new CustomException("Creator user not found", HttpStatus.NOT_FOUND));

        Users assignedUser = userRepository.findById(dto.getAssignedTo())
                .orElseThrow(() -> new CustomException("Assigned user not found", HttpStatus.NOT_FOUND));

        Sprint sprint = null;
        if (dto.getSprintId() != null) {
            sprint = sprintRepository.findById(dto.getSprintId())
                    .orElseThrow(() -> new CustomException("Sprint not found", HttpStatus.NOT_FOUND));

            // Optional: ensure sprint belongs to same project
            if (!sprint.getProject().getId().equals(project.getId())) {
                throw new CustomException("Sprint does not belong to this project", HttpStatus.BAD_REQUEST);
            }
        }


        Tasks task = Tasks.builder()
                .taskId(TaskIdGenerator.generate())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .storyPoints(dto.getStoryPoints())
                .priority(dto.getPriority())
                .status(TaskStatus.CREATED) // default status value
                .project(project)
                .sprint(sprint)
                .assignedTo(assignedUser)
                .createdBy(creator)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        taskRepository.save(task);
        return map(task);
    }


    // assign task :
    public String assignTask(TaskAssignRequestDTO dto){

        Project project = projectRepository.findByProjectId(dto.getProjectId())
                .orElseThrow(() -> new CustomException("Project Not Found ", HttpStatus.NOT_FOUND));

        Tasks task=taskRepository.findByTaskId(dto.getTaskId())
                .orElseThrow(()-> new CustomException("Task not found ",HttpStatus.NOT_FOUND));

        Users users=userRepository.findByUserId(dto.getUserId())
                .orElseThrow(()-> new CustomException("User not found ",HttpStatus.NOT_FOUND));


        //  Check task belongs to project
        if (!task.getProject().getId().equals(project.getId())) {
            throw new CustomException("Task does not belong to this project", HttpStatus.BAD_REQUEST);
        }

        //  Check user exists
        Users user = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        // Check task belongs to the project :
        if(!users.getCreatedBy().getId().equals(project.getCreatedBy().getId())){
            throw new CustomException("User doesn't belong to this project",HttpStatus.BAD_REQUEST);
        }

        //active tasks :
        Long activeTasks = taskRepository.countActiveTasksForUser(
                users.getId(),
                List.of(TaskStatus.ASSIGNED, TaskStatus.IN_PROGRESS)
        );


        if (activeTasks >= 2) {
            throw new CustomException("User already has 2 active tasks", HttpStatus.BAD_REQUEST);
        }

        // assign task :
        task.setAssignedTo(user);
        task.setStatus(TaskStatus.ASSIGNED);
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);

        return "Task assigned successfully!";

    }



    // Update Task details using its id :
    public String updateTaskStatus(Long id, TaskStatusUpdateDTO dto){

    Tasks task = taskRepository.findById(id)
                .orElseThrow(()-> new CustomException("Task Not found",HttpStatus.NOT_FOUND));

        TaskStatus oldStatus=task.getStatus();
        TaskStatus newStatus=task.getStatus();


        // Validate allowed transitions
        if (!isAllowedTransition(oldStatus, newStatus)) {
            throw new CustomException(
                    "Invalid transition: " + oldStatus + " → " + newStatus,
                    HttpStatus.BAD_REQUEST
            );
        }


        // Update status :
        task.setStatus(newStatus);
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);

        return "Status Updated successfully !";
    }


    // status transition logic :
    private boolean isAllowedTransition(TaskStatus oldStatus,TaskStatus newStatus){

        switch (oldStatus){

            case CREATED :
                return newStatus == TaskStatus.ASSIGNED;

            case ASSIGNED:
                return newStatus == TaskStatus.ASSIGNED;

            case IN_PROGRESS:
                return newStatus == TaskStatus.IN_PROGRESS;

            case BLOCKED:
                return newStatus == TaskStatus.BLOCKED;

            default:
                return false;

        }
    }


    // Search :
    public Page<TaskResponseDTO> search(
            Long projectId,
            Long assignedToId,
            TaskStatus status,
            TaskPriority priority,
            LocalDate dueFrom,
            LocalDate dueTo,
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir,
            boolean useDueDate
    ) {
        // Validate referenced resources if present
        if (projectId != null && !projectRepository.existsById(projectId)) {
            throw new CustomException("Project not found", HttpStatus.NOT_FOUND);
        }
        if (assignedToId != null && !userRepository.existsById(assignedToId)) {
            throw new CustomException("Assigned user not found", HttpStatus.NOT_FOUND);
        }

        // Build spec
        var spec = TaskSpecification.filter(projectId, assignedToId, status, priority, dueFrom, dueTo, keyword, useDueDate);

        // Sort + Pageable
        String effectiveSortBy = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;
        Sort.Direction dir = (sortDir == null || sortDir.equalsIgnoreCase("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), Sort.by(dir, effectiveSortBy));

        // If filtering by createdAt using LocalDate range, convert to LocalDateTime bounds in an additional predicate is simpler — but our spec handles basic cases.
        Page<Tasks> results = taskRepository.findAll(spec, pageable);

        // map to DTO page
        return results.map(this::map);
    }




    private TaskResponseDTO map(Tasks t){
        return TaskResponseDTO.builder()
                .taskId(t.getTaskId())
                .title(t.getTitle())
                .description(t.getDescription())
                .storyPoints(t.getStoryPoints())
                .priority(t.getPriority())
                .status(t.getStatus())
                .projectId(t.getProject().getProjectId())
                .sprintId(t.getSprint() !=null ? t.getSprint().getSprintId() : null)
                .assignedTo(t.getAssignedTo().getName())
                .createdAt(t.getCreatedAt())
                .build();
    }
}