package com.polling.polling_api.dtos;


import com.polling.polling_api.models.Option;
import com.polling.polling_api.models.User;
import lombok.Data;

import java.util.List;

@Data
public class PollResponseDTO {
    private Long id;
    private String title;
    private String description;
    private User createdBy;
    private List<Option> options;
}
