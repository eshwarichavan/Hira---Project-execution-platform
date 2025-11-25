package com.example.projectexecutionplatform.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task_comments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Mappings :
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Tasks task;

    @ManyToOne
    @JoinColumn(name = "commented_by", nullable = false)
    private Users commentedBy;
}
