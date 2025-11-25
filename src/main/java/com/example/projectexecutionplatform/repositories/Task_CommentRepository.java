package com.example.projectexecutionplatform.repositories;

import com.example.projectexecutionplatform.models.entities.Task_comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Task_CommentRepository extends JpaRepository<Task_comments,Long> {
}
