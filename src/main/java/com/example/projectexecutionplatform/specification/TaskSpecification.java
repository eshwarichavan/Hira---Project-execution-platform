package com.example.projectexecutionplatform.specification;

import com.example.projectexecutionplatform.models.entities.Tasks;
import com.example.projectexecutionplatform.models.enums.TaskPriority;
import com.example.projectexecutionplatform.models.enums.TaskStatus;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Locale;

public final class TaskSpecification {

    private TaskSpecification(){}

    public static Specification<Tasks> filter(
            Long projectId,
            Long assignedToId,
            TaskStatus status,
            TaskPriority priority,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            boolean useDueDateField
    ){

        return (root ,query,cb)->{
            Predicate predicate=cb.conjunction();

            // Joining project & assignedTo for filter
            Join<Tasks,?> projectJoin = root.join("project", JoinType.INNER);
            Join<Tasks,?> assignedJoin = root.join("assignedTo", JoinType.INNER);


            // Project Filter :
            if(projectId != null){
                predicate = cb.and(predicate, cb.equal(projectJoin.get("id"), projectId));
            }

            // Assigned User Filter :
            if(assignedToId != null){
                predicate = cb.and(predicate, cb.equal(assignedJoin.get("id"),assignedToId));
            }

            // Status Filter :
            if(status != null){
                predicate = cb.and(predicate, cb.equal(root.get("status"),status));
            }

            // Priority Filter :
            if(priority != null){
                predicate = cb.and(predicate, cb.equal(root.get("Priority"),priority));
            }

            // Date range Filter :
            if(startDate != null){
                if(useDueDateField){
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("dueDate"),startDate));
                }else{
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDate.class),startDate));
                }
            }

            if(endDate != null){
                if(useDueDateField){
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("dueDate"),endDate));
                }else{
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt").as(LocalDate.class),endDate));
                }
            }

            // Keyword Search :
            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.toLowerCase(Locale.ROOT).trim() + "%";
                Expression<String> titleLower = cb.lower(root.get("title"));
                Expression<String> descLower = cb.lower(root.get("description"));
                Predicate keywordPred = cb.or(cb.like(titleLower, pattern), cb.like(descLower, pattern));
                predicate = cb.and(predicate, keywordPred);
            }

            return predicate;
        };


    }
}



