package com.example.taskly.task.exception;

import com.example.taskly.ExceptionHandler.TasklyException;
import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends TasklyException {
    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}