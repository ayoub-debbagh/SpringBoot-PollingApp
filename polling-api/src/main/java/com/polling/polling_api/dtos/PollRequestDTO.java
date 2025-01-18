package com.polling.polling_api.dtos;


import com.polling.polling_api.models.Option;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PollRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;

    @NotNull(message = "Expiration date is required")
    @Future(message = "Expiration date must be in the future")
    private LocalDateTime expirationDate;

    private List<Option> options;
}
