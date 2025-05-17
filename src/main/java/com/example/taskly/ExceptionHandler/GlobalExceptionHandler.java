package com.example.taskly.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            if (invalidFormatException.getTargetType().isEnum()) {
                String field = invalidFormatException.getPath().get(0).getFieldName();
                String value = invalidFormatException.getValue().toString();
                Object[] enumConstants = invalidFormatException.getTargetType().getEnumConstants();
                String allowedValues = Arrays.toString(Arrays.stream(enumConstants).map(Object::toString).toArray());

                String message = "Invalid value for field '" + field + "': '" + value + "'. Allowed values: " + allowedValues;
                return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(message));
            }
            String message = "Invalid input format: " + invalidFormatException.getMessage();
            return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(message));
        }
        String message = "Malformed JSON request: " + ex.getMessage();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllOtherExceptions(Exception ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Unexpected error occurred";
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(message));
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, List<String> messages) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("messages", messages);
        return new ResponseEntity<>(body, status);
    }
}