package gtp.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * REST Controller for serving the home page and general application information.
 * Separate from the Task API controller to maintain clean separation of concerns.
 */
@RestController
@RequestMapping("/")
@Tag(name = "Home Controller", description = "Serves the application home page and general information")
public class HomePageController {

    private final ResourceLoader resourceLoader;

    public HomePageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Returns the application home page with API information.
     *
     * @return HTML welcome page
     */
    @Operation(
            summary = "Home page",
            description = "Returns the application home page with API information and documentation links",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Home page displayed successfully"
                    )
            }
    )
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/home.html");
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return """
                    <!DOCTYPE html>
                    <html>
                    <head><title>Task Management API</title></head>
                    <body>
                        <h1>Task Management API</h1>
                        <p>Welcome to the Task Management REST API</p>
                        <p>Visit <a href="/swagger-ui.html">Swagger UI</a> for documentation</p>
                    </body>
                    </html>
                    """;
        }
    }
}