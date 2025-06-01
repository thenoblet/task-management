
### SPRING_CONCEPTS


# Spring Framework Concepts

## Inversion of Control (IoC)

IoC is a design principle where the control of object creation and dependency injection is handed over to a container (in this case, the Spring IoC container). Instead of manually creating and managing objects, the framework handles these responsibilities.

Key aspects:
- Objects define their dependencies through configuration (annotations or XML)
- The container creates and wires objects together
- Promotes loose coupling between components

In this project:
- `@SpringBootApplication` triggers component scanning
- Beans are defined through stereotypes:
    - `@Repository` for `InMemoryTaskRepository`
    - `@Service` for `TaskServiceImpl`
    - `@RestController` for `TaskController`
- The container manages the lifecycle of these components

**What are the Benefits?**
- Decouples component creation from usage
- Simplifies application configuration
- Makes testing easier (components can be easily mocked)


## Dependency Injection (DI)

DI is the mechanism that implements IoC. It's the process of supplying dependencies to objects rather than having them construct dependencies themselves.

Spring provides DI through:
- Constructor injection (recommended)
- Setter injection
- Field injection

**Types of DI:**
1. **Constructor Injection** (my preferred approach):
    - Dependencies are final/immutable
    - Clearly visible dependencies
    - Ensures fully initialized objects

Example from the project:
```java
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired // Constructor injection
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    // ...
}
```

2. Setter Injection:
   ```java
   @Autowired
   public void setTaskService(TaskService taskService) {
       this.taskService = taskService;
   }
   ```

3. Field Injection (discouraged):
   ```java
   @Autowired
   private TaskService taskService;
   ```


## Application Context

The Spring container that manages all beans and their dependencies.

**Key features:**
- Created automatically via `SpringApplication.run()`
- Contains all registered beans
- Provides:
    - Dependency injection
    - Lifecycle management
    - Event publication
    - Resource access

## Bean Scopes

Spring manages beans with different lifecycles:

| Scope       | Description                          | Our Usage            |
|-------------|--------------------------------------|----------------------|
| Singleton   | One instance per container (default) | All our services     |
| Prototype   | New instance each request            | Not used             |
| Request     | Web request scope                    | Not applicable       |
| Session     | User session scope                   | Not applicable       |

## Aspect-Oriented Programming (AOP)

Cross-cutting concerns like logging and transaction management.

**Example potential use in this project:**
```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* gtp.taskmanagement.service.*.*(..))")
    public void logServiceMethods(JoinPoint jp) {
        logger.info("Executing: " + jp.getSignature());
    }
}
```

## Spring MVC Architecture

Our REST API follows Spring MVC patterns:

```
HTTP Request → DispatcherServlet → Controller → Service → Repository → Response
```

**Key annotations:**
- `@RestController`: Combines `@Controller` and `@ResponseBody`
- `@RequestMapping`: Maps HTTP requests to handler methods
- `@GetMapping`/`@PostMapping`: Specific HTTP method mappings
- `@RequestBody`: Binds HTTP request body to method parameter
- `@ResponseStatus`: Configures HTTP response status

## Spring Boot Autoconfiguration

Magic behind our simple setup:

1. Detects libraries in classpath
2. Configures sensible defaults
3. Provides:
    - Embedded Tomcat server
    - Spring MVC setup
    - JSON binding
    - Error handling
    - Actuator endpoints

## Testing Support

**Key testing features we could use:**
```java
@SpringBootTest
class TaskServiceTests {
    @Autowired
    private TaskService service;
    
    @MockBean
    private TaskRepository repository;
    
    @Test
    void getTask_NotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> service.getTaskById(UUID.randomUUID()));
    }
}
```

## Transaction Management

(Note: Not used in my in-memory implementation, but important for databases)

```java
@Service
public class TaskServiceImpl {
    @Transactional
    public Task updateTask(UUID id, Task updates) {
        // Atomic operation
    }
}
```

## Best Practices Demonstrated

1. **Layered Architecture**:
    - Controller → Service → Repository
2. **Dependency Injection**:
    - Constructor injection
    - Clear dependencies
3. **Separation of Concerns**:
    - DTOs vs Entities
    - Business logic in service layer
4. **RESTful Design**:
    - Proper HTTP methods
    - Meaningful status codes
5. **Validation**:
    - Input validation at API boundaries

## Common Spring Annotations Cheat Sheet

| Annotation          | Purpose                                | Our Usage Example              |
|---------------------|----------------------------------------|--------------------------------|
| `@Component`        | Generic Spring bean                   | Base for other stereotypes     |
| `@Service`          | Business logic component              | `TaskServiceImpl`              |
| `@Repository`       | Data access component                 | `TaskRepository`       |
| `@RestController`   | REST API controller                   | `TaskController`               |
| `@Autowired`        | Dependency injection                  | Constructor injection          |
| `@Configuration`    | Configuration class                   | `SwaggerConfig`                |
| `@Bean`             | Method producing a bean               | Swagger `OpenAPI` bean         |
| `@Value`            | Inject property values                | Not used (but available)       |
| `@Profile`          | Profile-specific configuration        | Could use for dev vs prod      |
| `@Scheduled`        | Scheduled task execution              | Potential for cleanup tasks    |

```
