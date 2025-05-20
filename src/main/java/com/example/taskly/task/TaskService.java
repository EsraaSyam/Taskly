package com.example.taskly.task;

import com.example.taskly.task.dto.request.CreateTaskRequest;
import com.example.taskly.task.dto.request.UpdateTaskRequest;
import com.example.taskly.task.dto.response.TaskResponseDTO;
import com.example.taskly.task.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<TaskEntity> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::entityToResponse)
                .toList();
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




}
