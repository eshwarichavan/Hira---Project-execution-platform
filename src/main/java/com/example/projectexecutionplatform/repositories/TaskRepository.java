package com.example.projectexecutionplatform.repositories;

import com.example.projectexecutionplatform.models.entities.Tasks;
import com.example.projectexecutionplatform.models.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Tasks,Long>, JpaSpecificationExecutor<Tasks> {

    @Query("""
    SELECT COUNT(t)
    FROM Tasks t
    WHERE t.assignedTo.id = :userId
    AND t.status IN :statuses
    """)
    Long countActiveTasksForUser(Long id, List<TaskStatus> assigned);


    Optional<Tasks> findByTaskId(String taskId);

    boolean existsByTitle(String title);
}
