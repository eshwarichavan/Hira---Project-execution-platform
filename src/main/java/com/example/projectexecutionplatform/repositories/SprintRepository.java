package com.example.projectexecutionplatform.repositories;

import com.example.projectexecutionplatform.models.entities.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {

    Optional<Sprint> findBySprintId(String sprintId);
}
