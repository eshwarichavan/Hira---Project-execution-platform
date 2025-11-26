package com.example.projectexecutionplatform.models.entities;

import com.example.projectexecutionplatform.models.enums.TaskPriority;
import com.example.projectexecutionplatform.models.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "storyPoints")
    private Integer storyPoints;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "due_date")
    private LocalDate dueDate;


    // Injecting enum :
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="status")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "priority")
    private TaskPriority priority;

    // Mappings :
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    private Users assignedTo;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Users createdBy;
}
