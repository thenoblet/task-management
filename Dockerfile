
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy Maven wrapper files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Run the application
ENTRYPOINT ["java", "-jar", "target/task-management-0.0.1-SNAPSHOT.jar"]

# Expose port 8080
EXPOSE 8080