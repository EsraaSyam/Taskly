package com.example.taskly.task;

import com.example.taskly.task.dto.request.CreateTaskRequest;
import com.example.taskly.task.dto.request.SearchTaskRequest;
import com.example.taskly.task.dto.request.UpdateTaskRequest;
import com.example.taskly.task.dto.response.TaskResponseDTO;
import com.example.taskly.task.enums.LogicalOPerator;
import com.example.taskly.task.enums.TaskStatus;
import com.example.taskly.task.exception.TaskNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDTO createTask(CreateTaskRequest taskRequestDTO) {
        TaskEntity taskEntity = taskMapper.taskToEntity(taskRequestDTO);
        TaskEntity savedTask = taskRepository.save(taskEntity);
        return taskMapper.entityToResponse(savedTask);
    }

    public TaskResponseDTO getTask(long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.entityToResponse(taskEntity);
    }

    public TaskEntity getTaskEntity(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<TaskResponseDTO> getAllTasks() {
        List<TaskResponseDTO> responses = taskRepository.findAll().stream()
                .map(taskMapper::entityToResponse)
                .collect(Collectors.toList());
        responses.sort(Comparator.comparing(TaskResponseDTO::getId));
        return responses;
    }


    public TaskResponseDTO updateTask(long id, UpdateTaskRequest TaskRequestDTO) {
        TaskEntity taskEntity = this.getTaskEntity(id);

        if (TaskRequestDTO.getTitle() != null) {
            taskEntity.setTitle(TaskRequestDTO.getTitle());
        }
        if (TaskRequestDTO.getDescription() != null) {
            taskEntity.setDescription(TaskRequestDTO.getDescription());
        }
        if (TaskRequestDTO.getStatus() != null) {
            taskEntity.setStatus(TaskRequestDTO.getStatus());
        }

        TaskEntity updatedTask = taskRepository.save(taskEntity);
        return taskMapper.entityToResponse(updatedTask);
    }

    public void hardDeleteTask(long id) {
        TaskEntity taskEntity = this.getTaskEntity(id);
        taskRepository.delete(taskEntity);
    }

    public void softDeleteTask(long id) {
        TaskEntity taskEntity = this.getTaskEntity(id);
        taskEntity.setDeleted(true);
        taskRepository.save(taskEntity);
    }

    public List<TaskResponseDTO> searchTasks(SearchTaskRequest searchTaskRequest) {
        Specification<TaskEntity> spec = Specification.where(null);

        String title = searchTaskRequest.getTitle();
        String description = searchTaskRequest.getDescription();
        TaskStatus status = searchTaskRequest.getStatus();
        LogicalOPerator op = searchTaskRequest.getLogicalOPerator();

        if (op == LogicalOPerator.AND) {
            spec = Specification.where(TaskSpecification.hasTitle(title))
                    .and(TaskSpecification.hasDescription(description))
                    .and(TaskSpecification.hasStatus(status));
        } else if (op == LogicalOPerator.OR) {
            spec = Specification.where(TaskSpecification.hasTitle(title))
                    .or(TaskSpecification.hasDescription(description))
                    .or(TaskSpecification.hasStatus(status));
        }

        return taskRepository.findAll(spec).stream()
                .map(taskMapper::entityToResponse)
                .sorted(Comparator.comparing(TaskResponseDTO::getId))
                .collect(Collectors.toList());
    }
}
