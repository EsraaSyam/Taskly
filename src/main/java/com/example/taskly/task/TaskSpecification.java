package com.example.taskly.task;

import com.example.taskly.task.enums.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    public static Specification<TaskEntity> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<TaskEntity> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                description == null ? null : criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<TaskEntity> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

}
