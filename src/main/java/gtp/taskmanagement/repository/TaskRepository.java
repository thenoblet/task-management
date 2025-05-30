package gtp.taskmanagement.repository;

import gtp.taskmanagement.model.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    List<Task> findAll();
    Optional<Task> findById(UUID id);
    Task save(Task task);
    void deleteById(UUID id);

    List<Task> findByStatus(Task.Status status);

    List<Task> findByPriority(Task.Priority priority);
}
