package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.exceptions.CustomException;
import com.example.projectexecutionplatform.models.dtos.*;
import com.example.projectexecutionplatform.models.entities.*;
import com.example.projectexecutionplatform.models.enums.TaskPriority;
import com.example.projectexecutionplatform.models.enums.TaskStatus;
import com.example.projectexecutionplatform.repositories.*;
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

    @Autowired
    private TaskAuditRepository taskAuditRepository;

    // create task :
    public TaskResponseDTO createTask(TaskCreateRequestDTO dto){

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));

        Users creator = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(() -> new CustomException("Creator user not found", HttpStatus.NOT_FOUND));

        Users assignedUser = userRepository.findById(dto.getAssignedTo())
                .orElseThrow(() -> new CustomException("Assigned user not found", HttpStatus.NOT_FOUND));

        //for duplicate entries :
        if (taskRepository.existsByTitle(dto.getTitle())) {
            throw new CustomException("Task with this title already exists", HttpStatus.BAD_REQUEST);
        }


        Sprint sprint = null;
        if (dto.getSprintId() != null) {
            sprint = sprintRepository.findById(dto.getSprintId())
                    .orElseThrow(() -> new CustomException("Sprint not found", HttpStatus.NOT_FOUND));

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

        Users user = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        if(users.getCreatedBy() == null || project.getCreatedBy() == null) {
            throw new CustomException("User or Project creator details missing", HttpStatus.BAD_REQUEST);
        }

        if(!users.getCreatedBy().getId().equals(project.getCreatedBy().getId())) {
            throw new CustomException("User doesn't belong to this project", HttpStatus.BAD_REQUEST);
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
    public String updateTaskStatus(String task_id, TaskStatusUpdateDTO dto) {


        Tasks task = taskRepository.findByTaskId(task_id)
                .orElseThrow(() -> new CustomException("Task Not found", HttpStatus.NOT_FOUND));



        // for active user & archived projects :
        Users assignedUser=task.getAssignedTo();
        Project project=task.getProject();


        // reject if user is inactive :
        if(!assignedUser.isActive()){
            throw new CustomException("Cannot update Task . Assigned User is INACTIVE",HttpStatus.BAD_REQUEST);
        }

        // reject if project is archived :
        if(project.isArchived()){
            throw new CustomException("Cannot update Task . Project is archived.",HttpStatus.BAD_REQUEST);
        }


        // for statuses :
        TaskStatus oldStatus = task.getStatus();
        TaskStatus newStatus = dto.getNewStatus();

        // audit entry :
        TaskAudit audit = TaskAudit.builder()
                .taskId(task.getTaskId())
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .updatedBy(dto.UpdatedBy)
                .updatedAt(LocalDateTime.now())
                .build();

        taskAuditRepository.save(audit);


        // Validate allowed transitions
        if (!isAllowedTransition(oldStatus, newStatus,task)) {
            throw new CustomException(
                    "Invalid transition: " + oldStatus + " => " + newStatus,
                    HttpStatus.BAD_REQUEST
            );
        }


        // Update status :
        task.setStatus(newStatus);
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);

        return "Status Updated successfully !";
    }


    // Audit History :
    public  Page<TaskAuditResponseDTO> getAuditHistory(
            String taskId,
            int page,
            int size,
            String sortDir
    ){
        Sort sort=sortDir.equalsIgnoreCase("ASC") ?
                Sort.by("updatedAt").ascending() :
                Sort.by("updatedAt").descending();

        Pageable pageable=PageRequest.of(page,size,sort);

        Page<TaskAudit> audits=taskAuditRepository.findByTaskId(taskId,pageable);

        return audits.map(this::mapAudit);

    }



    // status transition logic :
    private boolean isAllowedTransition(TaskStatus oldStatus, TaskStatus newStatus, Tasks task){

        if(oldStatus == TaskStatus.CLOSED){
            throw new CustomException("Cannot update status of a CLOSED task. ",HttpStatus.BAD_REQUEST);
        }


        if(newStatus == TaskStatus.COMPLETED){
            if(task.getDueDate() == null || task.getAssignedTo() == null){
                throw new CustomException("Cannot mark as COMPLETE . Required fields are missing (dueDate or assignedMembers",HttpStatus.BAD_REQUEST);
            }
        }

         switch (oldStatus){
            case CREATED :
                return newStatus == TaskStatus.ASSIGNED;

            case ASSIGNED:
                return newStatus == TaskStatus.IN_PROGRESS;

            case IN_PROGRESS:
                return newStatus == TaskStatus.BLOCKED
                        || newStatus == TaskStatus.COMPLETED;

            case BLOCKED:
                return newStatus == TaskStatus.IN_PROGRESS;

            case COMPLETED:
                return newStatus == TaskStatus.CLOSED;

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

        if (projectId != null && !projectRepository.existsById(projectId)) {
            throw new CustomException("Project not found", HttpStatus.NOT_FOUND);
        }
        if (assignedToId != null && !userRepository.existsById(assignedToId)) {
            throw new CustomException("Assigned user not found", HttpStatus.NOT_FOUND);
        }


        var spec = TaskSpecification.filter(projectId, assignedToId, status, priority, dueFrom, dueTo, keyword, useDueDate);

        // Sort & Pageable :
        String effectiveSortBy = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;
        Sort.Direction dir = (sortDir == null || sortDir.equalsIgnoreCase("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), Sort.by(dir, effectiveSortBy));

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


    // task audit mapper :
    private TaskAuditResponseDTO mapAudit(TaskAudit audit){
        TaskAuditResponseDTO dto=new TaskAuditResponseDTO();
        dto.setTaskId(audit.getTaskId());
        dto.setOldStatus(audit.getOldStatus());
        dto.setNewStatus(audit.getNewStatus());
        dto.setUpdatedBy(audit.getUpdatedBy());
        dto.setUpdatedAt(audit.getUpdatedAt());
        return dto;
    }
}