package com.example.projectexecutionplatform.repositories;

import com.example.projectexecutionplatform.models.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Tasks,Long> {
}
