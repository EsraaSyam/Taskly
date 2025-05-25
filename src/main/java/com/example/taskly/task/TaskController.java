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
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid CreateTaskRequest taskRequestDTO) {
        TaskResponseDTO response = taskService.createTask(taskRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
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

    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<Void> softDeleteTask(@PathVariable long id) {
        taskService.softDeleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDeleteTask(@PathVariable long id) {
        taskService.hardDeleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
