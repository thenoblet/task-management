# Task Manager API 🚀

![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.0-green.svg)
![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Docker](https://img.shields.io/badge/Docker-✓-blue.svg)
![Swagger](https://img.shields.io/badge/Swagger-UI-important)

A robust **Spring Boot** REST API for task management with full CRUD operations, built with modern best practices.

## Features ✨

- **Complete Task Management**:
    - Create, read, update, and delete tasks
    - Filter by status (`PENDING`, `COMPLETED`, `FAILED`, `IN_PROGRESS`)
    - Filter by priority (`HIGH`, `MEDIUM`, `LOW`)
    - Partial updates with `PATCH`

- **Modern Architecture**:
    - Clean layered design (Controller → Service → Repository)
    - Proper DTO separation
    - Comprehensive validation
    - Global exception handling

- **Production Ready**:
    - Docker containerization
    - Health checks via Actuator
    - Full API documentation (Swagger UI)
    - Proper HTTP status codes

## Tech Stack 💻

| Component           | Technology |
|---------------------|----------|
| Framework           | Spring Boot 3.x |
| Language            | Java 21 LTS |
| Build Tool          | Maven    |
| Containerization    | Docker   |
| API Documentation   | Swagger/OpenAPI 3 |
| Testing             | JUnit 5  |

## Getting Started 🏁

### Prerequisites

- Java 21 LTS
- Maven 3.8+
- Docker (optional)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/thenoblet/task-manager.git
   cd task-manager
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```

3. Run the application:
   ```bash
   java -jar target/task-management-*.jar
   ```

### Docker Setup

```bash
# Build the image
docker build -t task-manager .

# Run the container
docker run -p 8080:8080 task-manager
```

## API Documentation 📚

Interactive API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

![Swagger UI Preview](https://github.com/user-attachments/assets/3c4af18c-7554-488e-be5a-9f16f8215592)


## API Endpoints 🌐

| Method | Endpoint                | Description                     |
|--------|-------------------------|---------------------------------|
| GET    | `/api/v1/tasks`         | Get all tasks                   |
| GET    | `/api/v1/tasks/{id}`    | Get a specific task             |
| POST   | `/api/v1/tasks`         | Create a new task               |
| PUT    | `/api/v1/tasks/{id}`    | Fully update a task             |
| PATCH  | `/api/v1/tasks/{id}`    | Partially update a task         |
| DELETE | `/api/v1/tasks/{id}`    | Delete a task                   |
| GET    | `/api/v1/tasks/status/{status}` | Filter tasks by status      |
| GET    | `/api/v1/tasks/priority/{priority}` | Filter by priority     |

## Example Requests 💡

**Create a Task:**
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{
    "title": "Complete project",
    "description": "Finish the API documentation",
    "priority": "HIGH",
    "status": "IN_PROGRESS",
    "dueDate": "2023-12-31T23:59:59Z",
    "tags": ["documentation", "urgent"]
  }' \
  http://localhost:8080/api/v1/tasks
```

**Get Tasks by Status:**
```bash
curl http://localhost:8080/api/v1/tasks/status/IN_PROGRESS
```

## Project Structure 🗂️

```
task-manager-api/
├── src/
│   ├── main/
│   │   ├── java/gtp/taskmanagement/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Custom exceptions
│   │   │   ├── model/           # Domain models
│   │   │   ├── repository/      # Data access
│   │   │   └── service/         # Business logic
│   │   └── resources/           # Properties files
│   └── test/                    # Unit tests
├── docs/                        # Documentation
├── Dockerfile                   # Docker configuration
└── pom.xml                      # Maven dependencies
```

## Component Diagram 📊
```mermaid
graph TB
    %% External Components
    subgraph "Client Layer"
        Client["Client Application<br/>HTTP Requests"]
    end
    
    %% Spring Boot Framework
    subgraph "Spring Boot Framework"
        Dispatcher["DispatcherServlet"]
        Actuator["Spring Actuator<br/>Health/Metrics"]
        Swagger["Swagger UI<br/>API Documentation"]
    end
    
    %% Configuration Layer
    subgraph "Configuration"
        DataInit["DataInit<br/>Initial Data Setup"]
        SwaggerConfig["SwaggerConfig<br/>API Documentation Setup"]
    end
    
    %% Controller Layer
    subgraph "Controller Layer"
        TaskController["TaskController<br/>REST Endpoints<br/>• @RestController<br/>• Request/Response mapping<br/>• Validation"]
    end
    
    %% Service Layer
    subgraph "Service Layer"
        TaskService["TaskService (Interface)<br/>Business Contract"]
        TaskServiceImpl["TaskServiceImpl<br/>• Business Logic<br/>• Transaction Management<br/>• Domain Rules"]
    end
    
    %% Repository Layer
    subgraph "Repository Layer"
        TaskRepository["TaskRepository (Interface)<br/>Data Access Contract"]
        TaskRepositoryImpl["TaskRepositoryImpl<br/>• In-Memory Storage<br/>• ConcurrentHashMap<br/>• CRUD Operations"]
    end
    
    %% Data Transfer Objects
    subgraph "DTOs"
        TaskRequest["TaskRequest<br/>Input Validation"]
        TaskResponse["TaskResponse<br/>Output Format"]
        TaskUpdateRequest["TaskUpdateRequest<br/>Update Validation"]
    end
    
    %% Domain Model
    subgraph "Domain Model"
        Task["Task<br/>Core Entity<br/>• Business Rules<br/>• State Management"]
    end
    
    %% Exception Handling
    subgraph "Exception Handling"
        GlobalExceptionHandler["GlobalExceptionHandler<br/>• @ControllerAdvice<br/>• Centralized Error Handling"]
        TaskNotFoundException["TaskNotFoundException<br/>Custom Business Exception"]
    end
    
    %% Utilities
    subgraph "Utilities"
        Util["Util<br/>Helper Functions"]
    end
    
    %% Main Application
    subgraph "Application"
        TaskManagementApp["TaskManagementApplication<br/>@SpringBootApplication<br/>Main Entry Point"]
    end
    
    %% Relationships
    Client --> Dispatcher
    Dispatcher --> TaskController
    TaskController --> TaskServiceImpl
    TaskServiceImpl --> TaskRepositoryImpl
    TaskRepositoryImpl --> Task
    
    TaskController --> TaskRequest
    TaskController --> TaskResponse
    TaskController --> TaskUpdateRequest
    
    TaskService --> TaskServiceImpl
    TaskRepository --> TaskRepositoryImpl
    
    GlobalExceptionHandler --> TaskNotFoundException
    TaskController --> GlobalExceptionHandler
    
    SwaggerConfig --> Swagger
    DataInit --> TaskRepositoryImpl
    
    TaskManagementApp --> DataInit
    TaskManagementApp --> SwaggerConfig

%% Styling
    classDef clientStyle fill:#00bcd4,stroke:#006064,color:#fff
    classDef springStyle fill:#4caf50,stroke:#1b5e20,color:#fff
    classDef configStyle fill:#ff9800,stroke:#e65100,color:#fff
    classDef controllerStyle fill:#2196f3,stroke:#0d47a1,color:#fff
    classDef serviceStyle fill:#9c27b0,stroke:#4a148c,color:#fff
    classDef repoStyle fill:#8bc34a,stroke:#33691e,color:#fff
    classDef dtoStyle fill:#00acc1,stroke:#004d40,color:#fff
    classDef modelStyle fill:#ffc107,stroke:#f57c00,color:#000
    classDef exceptionStyle fill:#f44336,stroke:#b71c1c,color:#fff
    classDef utilStyle fill:#673ab7,stroke:#311b92,color:#fff
    classDef appStyle fill:#3f51b5,stroke:#1a237e,color:#fff
    
    class Client clientStyle
    class Dispatcher,Actuator,Swagger springStyle
    class DataInit,SwaggerConfig configStyle
    class TaskController controllerStyle
    class TaskService,TaskServiceImpl serviceStyle
    class TaskRepository,TaskRepositoryImpl repoStyle
    class TaskRequest,TaskResponse,TaskUpdateRequest dtoStyle
    class Task modelStyle
    class GlobalExceptionHandler,TaskNotFoundException exceptionStyle
    class Util utilStyle
    class TaskManagementApp appStyle
```
## Best Practices ✅

- **Inversion of Control** through Spring DI
- **Layered architecture** for separation of concerns
- **Immutable DTOs** for API contracts
- **Proper HTTP semantics** (status codes, methods)
- **Comprehensive validation** on all inputs
- **Containerization** for consistent deployments
