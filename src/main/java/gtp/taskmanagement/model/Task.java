/**
 * Represents a task entity in the task management system.
 * <p>
 * This class models a task with properties including title, description, priority,
 * status, due date, and tags. It includes validation constraints and automatic
 * timestamp generation for creation and modification times.
 * </p>
 *
 */
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
    private Status status;
    private Date dueDate;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Enum representing possible task statuses.
     */
    public enum Status {
        PENDING, COMPLETED, FAILED, IN_PROGRESS
    }

    /**
     * Enum representing task priority levels.
     */
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    /**
     * Default constructor that initializes the task with:
     * - A random UUID
     * - Current timestamp for creation and update times
     */
    public Task() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Constructs a new Task with the specified parameters.
     *
     * @param id The unique identifier for the task
     * @param title The task title (3-100 characters, required)
     * @param description The task description (max 500 characters)
     * @param priority The task priority (required)
     * @param status The task status
     * @param dueDate The due date (must be today or in the future)
     * @param tags List of tags (each tag max 20 characters)
     * @throws IllegalArgumentException If priority or status values are invalid
     */
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

    /**
     * @return The task's unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return The task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The task priority as an enum value
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @return The task priority as a String (enum name)
     */
    public String getPriorityAsString() {
        return priority != null ? priority.name() : null;
    }

    /**
     * @return The task status as an enum value
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return The task status as a String (enum name)
     */
    public String getStatusAsString() {
        return status != null ? status.name() : null;
    }

    /**
     * @return The task's due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @return List of tags associated with the task
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @return Timestamp when the task was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return Timestamp when the task was last updated
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the task title and updates the modification timestamp.
     * @param title The new title (3-100 characters)
     */
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the task description and updates the modification timestamp.
     * @param description The new description (max 500 characters)
     */
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the task priority from a String and updates the modification timestamp.
     * Only updates if the priority value is valid.
     * @param priority The new priority as a String
     */
    public void setPriority(String priority) {
        if (isValidPriority(priority)) {
            this.priority = Priority.valueOf(priority.toUpperCase());
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Sets the task priority and updates the modification timestamp.
     * @param priority The new priority as a Priority enum
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the task status from a String and updates the modification timestamp.
     * Only updates if the status value is valid.
     * @param status The new status as a String
     */
    public void setStatus(String status) {
        if (isValidStatus(status)) {
            this.status = Status.valueOf(status.toUpperCase());
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Sets the task status and updates the modification timestamp.
     * @param status The new status as a Status enum
     */
    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the task due date and updates the modification timestamp.
     * @param dueDate The new due date (must be today or in the future)
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the task tags and updates the modification timestamp.
     * @param tags The new list of tags (each tag max 20 characters)
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
        this.updatedAt = LocalDateTime.now();
    }
}