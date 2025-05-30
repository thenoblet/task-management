package gtp.taskmanagement.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public record TaskRequest(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100, message = "Title must be 3-100 characters")
        String title,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Priority is required")
        String priority,

        String status,

        @FutureOrPresent(message = "Due date must be today or in the future")
        Date dueDate,

        List<@Size(max = 20, message = "Each tag cannot exceed 20 characters") String> tags
) {}
