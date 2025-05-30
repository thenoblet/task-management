package gtp.taskmanagement.service;

import gtp.taskmanagement.dto.TaskResponse;
import gtp.taskmanagement.dto.TaskUpdateRequest;
import gtp.taskmanagement.exception.TaskNotFoundException;
import gtp.taskmanagement.model.Task;
import gtp.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public TaskResponse getTaskById(UUID id) {
        return taskRepository.findById(id)
                .map(TaskResponse::fromEntity)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    @Override
    public TaskResponse createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        return TaskResponse.fromEntity(savedTask);
    }

    @Override
    public TaskResponse updateTask(UUID id, Task taskUpdates) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        if (taskUpdates.getTitle() != null) {
            existingTask.setTitle(taskUpdates.getTitle());
        }
        if (taskUpdates.getDescription() != null) {
            existingTask.setDescription(taskUpdates.getDescription());
        }
        if (taskUpdates.getPriority() != null) {
            existingTask.setPriority(taskUpdates.getPriority());
        }
        if (taskUpdates.getStatus() != null) {
            existingTask.setStatus(taskUpdates.getStatus());
        }
        if (taskUpdates.getDueDate() != null) {
            existingTask.setDueDate(taskUpdates.getDueDate());
        }
        if (taskUpdates.getTags() != null) {
            existingTask.setTags(taskUpdates.getTags());
        }

        Task updatedTask = taskRepository.save(existingTask);
        return TaskResponse.fromEntity(updatedTask);
    }

    // In TaskServiceImpl.java
    @Override
    public TaskResponse patchTask(UUID id, TaskUpdateRequest updates) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        updates.title().ifPresent(task::setTitle);
        updates.description().ifPresent(task::setDescription);
        updates.priority().ifPresent(task::setPriority);
        updates.status().ifPresent(task::setStatus);
        updates.dueDate().ifPresent(task::setDueDate);
        updates.tags().ifPresent(task::setTags);

        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskResponse> getTasksByStatus(String status) {
        return taskRepository.findByStatus(Task.Status.valueOf(status.toUpperCase())).stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public List<TaskResponse> getTasksByPriority(String priority) {
        return taskRepository.findByPriority(Task.Priority.valueOf(priority.toUpperCase())).stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }
}
