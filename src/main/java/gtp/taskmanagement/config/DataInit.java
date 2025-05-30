package gtp.taskmanagement.config;


import gtp.taskmanagement.model.Task;
import gtp.taskmanagement.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
public class DataInit {

    private final TaskRepository taskRepository;

    public DataInit(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void init() {
        taskRepository.save(new Task(
                null,
                "Complete project setup",
                "Set up Spring Boot project with Docker",
                "HIGH",
                "IN_PROGRESS",
                new Date(System.currentTimeMillis() + 86400000), // Tomorrow
                List.of("backend", "urgent")
        ));

        taskRepository.save(new Task(
                null,
                "Write documentation",
                "Prepare API documentation",
                "MEDIUM",
                "PENDING",
                new Date(System.currentTimeMillis() + 259200000), // 3 days later
                List.of("docs")
        ));
    }
}