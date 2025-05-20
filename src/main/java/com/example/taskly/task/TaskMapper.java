package com.example.taskly.task;

import com.example.taskly.task.dto.request.CreateTaskRequest;
import com.example.taskly.task.dto.response.TaskResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskEntity taskToEntity(CreateTaskRequest taskRequestDTO) {
        if (taskRequestDTO == null) {
            throw new IllegalArgumentException("TaskRequestDTO cannot be null");
        }
        return TaskEntity.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .status(taskRequestDTO.getStatus())
                .build();
    }

    public TaskResponseDTO entityToResponse(TaskEntity taskEntity) {
        if (taskEntity == null) {
            throw new IllegalArgumentException("TaskEntity cannot be null");
        }
        return TaskResponseDTO.builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .status(taskEntity.getStatus())
                .createdAt(taskEntity.getCreatedAt())
                .updatedAt(taskEntity.getUpdatedAt())
                .build();
    }
}
