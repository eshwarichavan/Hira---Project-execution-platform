package com.example.projectexecutionplatform.repositories;

import com.example.projectexecutionplatform.models.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    Optional<Project> findByProjectId(String projectId);
}
