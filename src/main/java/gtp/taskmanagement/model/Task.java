package gtp.taskmanagement.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static gtp.taskmanagement.util.Util.isValidPriority;
import static gtp.taskmanagement.util.Util.isValidStatus;

public class Task {
    private UUID id;
    private String title;
    private String description;
    private Priority priority;
    private Status status; // Changed to Status enum
    private Date dueDate;
    private List<String> tags; // Added private modifier
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Status {
        PENDING, COMPLETED, FAILED, IN_PROGRESS
    }

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    public Task() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task(UUID id,
                @NotBlank(message = "Title is required")
                @Size(min = 3, max = 100, message = "Title must be 3-100 characters") String title,
                @Size(max = 500, message = "Description cannot exceed 500 characters") String description,
                @NotNull(message = "Priority is required") String priority,
                String status,
                @FutureOrPresent(message = "Due date must be today or in the future") Date dueDate,
                List<@Size(max = 20, message = "Each tag cannot exceed 20 characters") String> tags) {
        this();
        this.title = title;
        this.description = description;
        this.priority = isValidPriority(priority) ? Priority.valueOf(priority.toUpperCase()) : Priority.LOW;
        this.status = isValidStatus(status) ? Status.valueOf(status.toUpperCase()) : Status.PENDING;
        this.dueDate = dueDate;
        this.tags = tags;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getPriorityAsString() {
        return priority != null ? priority.name() : null;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusAsString() {
        return status != null ? status.name() : null;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPriority(String priority) {
        if (isValidPriority(priority)) {
            this.priority = Priority.valueOf(priority.toUpperCase());
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(String status) {
        if (isValidStatus(status)) {
            this.status = Status.valueOf(status.toUpperCase());
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
        this.updatedAt = LocalDateTime.now();
    }
}