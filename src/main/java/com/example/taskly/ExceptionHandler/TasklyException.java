package com.example.taskly.ExceptionHandler;

import org.springframework.http.HttpStatus;

public abstract class TasklyException extends RuntimeException {
    private final HttpStatus status;

    public TasklyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}