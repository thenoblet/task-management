package gtp.taskmanagement.controller;

import gtp.taskmanagement.dto.TaskRequest;
import gtp.taskmanagement.dto.TaskResponse;
import gtp.taskmanagement.dto.TaskUpdateRequest;
import gtp.taskmanagement.model.Task;
import gtp.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing tasks in the system.
 * Provides endpoints for CRUD operations and task filtering capabilities.
 * All endpoints are prefixed with /api/v1/tasks.
 */
@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task Controller", description = "Operations for managing tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * Constructs a new TaskController with the required TaskService.
     *
     * @param taskService The service handling task business logic
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Retrieves all tasks in the system.
     *
     * @return List of all tasks with HTTP 200 status
     */
    @Operation(
            summary = "Get all tasks",
            description = "Retrieves a list of all tasks in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved all tasks",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }


    /**
     * Retrieves a specific task by its ID.
     *
     * @param id UUID of the task to retrieve
     * @return Requested task with HTTP 200 status
     */
    @Operation(
            summary = "Get task by ID",
            description = "Retrieves a specific task by its unique identifier",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "UUID of the task to retrieve",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task found and returned",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }


    /**
     * Creates a new task in the system.
     *
     * @param request Task creation request containing task details
     * @return Created task with HTTP 201 status
     */
    @Operation(
            summary = "Create a new task",
            description = "Adds a new task to the system",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Task created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input provided"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Task object that needs to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskRequest.class)
                    ))
            @Valid @RequestBody TaskRequest request) {
        Task task = new Task(
                null,
                request.title(),
                request.description(),
                request.priority(),
                request.status(),
                request.dueDate(),
                request.tags()
        );
        return new ResponseEntity<>(
                taskService.createTask(task),
                HttpStatus.CREATED
        );
    }


    /**
     * Fully updates an existing task.
     *
     * @param id UUID of the task to update
     * @param request Task update request containing new values
     * @return Updated task with HTTP 200 status
     */
    @Operation(
            summary = "Update an entire task",
            description = "Replaces all fields of an existing task",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "UUID of the task to update",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input provided"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated task object",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskRequest.class)
                    )
            )
            @Valid @RequestBody TaskRequest request) {
        Task taskUpdates = new Task(
                id,
                request.title(),
                request.description(),
                request.priority(),
                request.status(),
                request.dueDate(),
                request.tags()
        );
        return ResponseEntity.ok(taskService.updateTask(id, taskUpdates));
    }


    /**
     * Partially updates an existing task.
     *
     * @param id UUID of the task to update
     * @param updates Task fields to update
     * @return Updated task with HTTP 200 status
     */
    @Operation(
            summary = "Partially update a task",
            description = "Updates specific fields of an existing task",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "UUID of the task to update",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task partially updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input provided"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found"
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> patchTask(
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Fields to update (only provided fields will be changed)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskUpdateRequest.class)
                    ))
            @Valid @RequestBody TaskUpdateRequest updates) {
        return ResponseEntity.ok(taskService.patchTask(id, updates));
    }


    /**
     * Deletes a task from the system.
     *
     * @param id UUID of the task to delete
     * @return Empty response with HTTP 204 status
     */
    @Operation(
            summary = "Delete a task",
            description = "Removes a task from the system",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "UUID of the task to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Task deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Filters tasks by their status.
     *
     * @param status Status to filter by
     * @return List of matching tasks with HTTP 200 status
     */
    @Operation(
            summary = "Filter tasks by status",
            description = "Retrieves tasks matching the specified status",
            parameters = {
                    @Parameter(
                            name = "status",
                            description = "Status to filter by (PENDING, COMPLETED, FAILED, IN_PROGRESS)",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(
                                    type = "string",
                                    allowableValues = {"PENDING", "COMPLETED", "FAILED", "IN_PROGRESS"}
                            )
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tasks filtered by status",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
                            )
                    )
            }
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }


    /**
     * Filters tasks by their priority level.
     *
     * @param priority Priority level to filter by
     * @return List of matching tasks with HTTP 200 status
     */
    @Operation(
            summary = "Filter tasks by priority",
            description = "Retrieves tasks matching the specified priority level",
            parameters = {
                    @Parameter(
                            name = "priority",
                            description = "Priority level to filter by",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tasks filtered by priority",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
                            )
                    )
            }
    )
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskResponse>> getTasksByPriority(
            @PathVariable String priority) {
        return ResponseEntity.ok(taskService.getTasksByPriority(priority));
    }
}