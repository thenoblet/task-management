package gtp.taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskUpdateRequest(
        Optional<@Size(min = 3, max = 100) String> title,
        Optional<@Size(max = 500) String> description,
        Optional<String> priority,
        Optional<String> status,
        Optional<Date> dueDate,
        Optional<List<@Size(max = 20) String>> tags
) {}
