package com.polling.polling_api.dtos;


import com.polling.polling_api.models.Option;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class PollRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private List<Option> options;
}
