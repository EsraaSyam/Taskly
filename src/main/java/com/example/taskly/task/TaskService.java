package com.example.taskly.task;

import com.example.taskly.task.dto.request.TaskRequestDTO;
import com.example.taskly.task.dto.response.TaskResponseDTO;
import org.springframework.stereotype.Service;

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

   
}
