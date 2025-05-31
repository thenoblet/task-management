package gtp.taskmanagement.config;

import gtp.taskmanagement.model.Task;
import gtp.taskmanagement.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Configuration class for initializing sample task data when the application starts.
 * <p>
 * This class automatically inserts predefined tasks into the database upon application startup
 * if no tasks exist. Useful for demonstration and testing purposes.
 * </p>
 *
 * @see Task
 * @see TaskRepository
 */
@Configuration
public class DataInit {

    private final TaskRepository taskRepository;

    /**
     * Constructs a new DataInit instance with the required TaskRepository.
     *
     * @param taskRepository The task repository used for data persistence
     */
    public DataInit(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Initializes sample task data after the application context is loaded.
     * <p>
     * Creates two sample tasks with different priorities, statuses, and due dates:
     * 1. A high-priority, in-progress project setup task
     * 2. A medium-priority documentation task
     * </p>
     */
    @PostConstruct
    public void init() {
        long oneDayInMillis = TimeUnit.DAYS.toMillis(1);
        long threeDaysInMillis = TimeUnit.DAYS.toMillis(3);

        taskRepository.save(new Task(
                null,
                "Complete project setup",
                "Set up Spring Boot project with Docker",
                "HIGH",
                "IN_PROGRESS",
                new Date(System.currentTimeMillis() + oneDayInMillis),
                List.of("backend", "urgent")
        ));

        taskRepository.save(new Task(
                null,
                "Write documentation",
                "Prepare API documentation",
                "MEDIUM",
                "PENDING",
                new Date(System.currentTimeMillis() + threeDaysInMillis),
                List.of("docs")
        ));
    }
}