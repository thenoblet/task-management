/**
 * Repository interface for managing {@link Task} entities in the task management system.
 * <p>
 * Provides basic CRUD operations and additional query methods for tasks.
 * Implementations of this interface are responsible for data persistence.
 * </p>
 *
 * @since 1.0
 */
package gtp.taskmanagement.repository;

import gtp.taskmanagement.model.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    /**
     * Retrieves all tasks from the repository.
     * @return List of all tasks (empty list if none found)
     */
    List<Task> findAll();

    /**
     * Finds a task by its unique identifier.
     * @param id The UUID of the task to find
     * @return Optional containing the task if found, empty otherwise
     */
    Optional<Task> findById(UUID id);

    /**
     * Saves a task to the repository.
     * <p>
     * If the task already exists (has an ID), it will be updated.
     * If the task is new (no ID), it will be inserted.
     * </p>
     * @param task The task to save
     * @return The saved task (with generated ID if new)
     */
    Task save(Task task);

    /**
     * Deletes a task by its unique identifier.
     * @param id The UUID of the task to delete
     * @throws IllegalArgumentException if no task exists with the given ID
     */
    void deleteById(UUID id);

    /**
     * Finds all tasks with the specified status.
     * @param status The status to filter by (cannot be null)
     * @return List of matching tasks (empty list if none found)
     */
    List<Task> findByStatus(Task.Status status);

    /**
     * Finds all tasks with the specified priority.
     * @param priority The priority to filter by (cannot be null)
     * @return List of matching tasks (empty list if none found)
     */
    List<Task> findByPriority(Task.Priority priority);
}