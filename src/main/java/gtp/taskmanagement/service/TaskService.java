package gtp.taskmanagement.service;

import gtp.taskmanagement.dto.TaskResponse;
import gtp.taskmanagement.dto.TaskUpdateRequest;
import gtp.taskmanagement.model.Task;
import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<TaskResponse> getAllTasks();
    TaskResponse getTaskById(UUID id);
    TaskResponse createTask(Task task);
    TaskResponse updateTask(UUID id, Task task);

    TaskResponse patchTask(UUID id, TaskUpdateRequest updates);

    void deleteTask(UUID id);
    List<TaskResponse> getTasksByStatus(String status);

    List<TaskResponse> getTasksByPriority(String priority);
}