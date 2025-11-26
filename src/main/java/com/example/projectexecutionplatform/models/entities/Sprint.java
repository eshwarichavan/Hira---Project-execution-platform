package com.example.projectexecutionplatform.models.entities;

import com.example.projectexecutionplatform.models.enums.SprintStatus;
import com.example.projectexecutionplatform.models.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sprint")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sprint_id", nullable = false, unique = true)
    private String sprintId;  // Public ID

    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;


    // Injecting enum :
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, name="task_status")
//    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="sprint_status")
    private SprintStatus sprintStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // Mappings :
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Users createdBy;
}
