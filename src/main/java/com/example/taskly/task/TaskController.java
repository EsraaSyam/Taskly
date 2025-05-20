package com.example.taskly.task;


import com.example.taskly.task.dto.request.CreateTaskRequest;
import com.example.taskly.task.dto.request.UpdateTaskRequest;
import com.example.taskly.task.dto.response.TaskResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid CreateTaskRequest taskRequestDTO) {
        TaskResponseDTO response = taskService.createTask(taskRequestDTO);
        return ResponseEntity
                .created(URI.create("/task/" + response.getId()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable long id) {
        TaskResponseDTO response = taskService.getTask(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> response = taskService.getAllTasks();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable long id, @RequestBody @Valid UpdateTaskRequest taskRequestDTO) {
        TaskResponseDTO response = taskService.updateTask(id, taskRequestDTO);
        return ResponseEntity.ok(response);
    }

}
