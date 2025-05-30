package gtp.taskmanagement.repository;

import gtp.taskmanagement.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private final Map<UUID, Task> tasks = new ConcurrentHashMap<>();

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task = new Task(
                    UUID.randomUUID(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriorityAsString(),
                    task.getStatusAsString(),
                    task.getDueDate(),
                    task.getTags()
            );
        }
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void deleteById(UUID id) {
        tasks.remove(id);
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return tasks.values().stream()
                .filter(task -> status.equals(task.getStatus()))
                .toList();
    }

    @Override
    public List<Task> findByPriority(Task.Priority priority) {
        return tasks.values().stream()
                .filter(task -> priority.equals(task.getPriority()))
                .toList();
    }

    public void clear() {
        tasks.clear();
    }
}