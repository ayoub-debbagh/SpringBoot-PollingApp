package com.polling.polling_api.dtos;

import lombok.Data;

@Data
public class PollResultsDTO {
    private Long optionId;
    private String optionText;
    private Long voteCount;
    private Long userId;
}
