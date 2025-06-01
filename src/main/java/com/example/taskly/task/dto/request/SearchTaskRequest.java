package com.example.taskly.task.dto.request;

import com.example.taskly.task.enums.LogicalOPerator;
import com.example.taskly.task.enums.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchTaskRequest {
    @Size(max = 60, message = "Title must be less than 100 characters")
    private String title;
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;
    private TaskStatus status;
    private LogicalOPerator logicalOPerator = LogicalOPerator.AND;
}
