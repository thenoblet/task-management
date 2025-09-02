package gtp.taskmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 * Configures the API documentation that will be automatically generated
 * and available at /swagger-ui.html when the application is running.
 *
 * @see "OpenAPI Specification at https://swagger.io/specification/"
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation for the Task Management System.
     * Includes server information, API metadata, and license details.
     *
     * @return Configured OpenAPI object with API documentation settings
     */
    @Bean
    public OpenAPI taskManagerOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .url("http://tms-app-env.eba-d2idh7xk.eu-central-1.elasticbeanstalk.com/")
                        .description("Production server"))
                .info(new Info()
                        .title("Task Management API")
                        .description("API for managing tasks with full CRUD operations. "
                                + "Features include task creation with priority and status, "
                                + "filtering capabilities, tag management, and due date tracking.")
                        .version("v1.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}