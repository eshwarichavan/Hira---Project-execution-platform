package com.example.projectexecutionplatform.controllers;

import com.example.projectexecutionplatform.models.dtos.TaskAssignRequestDTO;
import com.example.projectexecutionplatform.models.dtos.TaskCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.TaskResponseDTO;
import com.example.projectexecutionplatform.models.dtos.TaskStatusUpdateDTO;
import com.example.projectexecutionplatform.models.enums.TaskPriority;
import com.example.projectexecutionplatform.models.enums.TaskStatus;
import com.example.projectexecutionplatform.services.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/tasks")
@Validated
@Tag(name = "Tasks API" , description ="This is Task controller API to manage all the operations of task.")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping("/create")
    @Operation(summary = "Create a new task")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskCreateRequestDTO dto) {
        return taskService.createTask(dto);
    }


    // assign task :
    @PostMapping("/assign")
    @Operation(summary = "assigns the task to the user")
    public ResponseEntity<?> assignTask(@Valid @RequestBody TaskAssignRequestDTO dto) {
        return ResponseEntity.ok(taskService.assignTask(dto));
    }



    // update status :
    @PutMapping("/status/{id}")
    @Operation(summary = "updates the task to the user")
    public ResponseEntity<?> updateStatus(
            @Valid
            @PathVariable Long id,
            @RequestBody TaskStatusUpdateDTO dto) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id,dto));
    }


    // search :
    @GetMapping("/search")
    @Operation(summary = "Search the tasks")
    public Page<TaskResponseDTO> searchTasks(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long assignedToId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueTo,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,
            @RequestParam(defaultValue = "true") boolean useDueDate
    ) {
        return taskService.search(
                projectId,
                assignedToId,
                status,
                priority,
                dueFrom,
                dueTo,
                keyword,
                page,
                size,
                sortBy,
                sortDir,
                useDueDate
        );
    }
}

