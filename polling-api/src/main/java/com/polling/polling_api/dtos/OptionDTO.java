package com.polling.polling_api.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OptionDTO {
    private String text;
    private Long voteCount;
    private Long pollId;
}
