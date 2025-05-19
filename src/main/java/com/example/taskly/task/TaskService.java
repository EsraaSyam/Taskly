package com.example.taskly.task;

import com.example.taskly.task.dto.request.TaskRequestDTO;
import com.example.taskly.task.dto.response.TaskResponseDTO;
import com.example.taskly.task.exception.TaskNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        TaskEntity taskEntity = taskMapper.taskToEntity(taskRequestDTO);
        TaskEntity savedTask = taskRepository.save(taskEntity);
        return taskMapper.entityToResponse(savedTask);
    }

    public TaskResponseDTO getTask(long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.entityToResponse(taskEntity);
    }

    public List<TaskResponseDTO> getAllTasks() {
        List<TaskEntity> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::entityToResponse)
                .toList();
    }


}
