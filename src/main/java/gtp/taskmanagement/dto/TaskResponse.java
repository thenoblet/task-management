package gtp.taskmanagement.dto;

import gtp.taskmanagement.model.Task;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        String priority,
        String status,
        Date dueDate,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriorityAsString(),
                task.getStatusAsString(),
                task.getDueDate(),
                task.getTags(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
