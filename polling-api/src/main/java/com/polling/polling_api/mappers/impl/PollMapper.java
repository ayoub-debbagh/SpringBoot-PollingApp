package com.polling.polling_api.mappers.impl;


import com.polling.polling_api.dtos.PollResponseDTO;
import com.polling.polling_api.mappers.Mapper;
import com.polling.polling_api.models.Poll;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PollMapper implements Mapper<Poll, PollResponseDTO> {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PollResponseDTO mapToDto(Poll poll) {
        return modelMapper.map(poll, PollResponseDTO.class);
    }

    @Override
    public Poll mapFromDto(PollResponseDTO pollResponseDTO) {
        return modelMapper.map(pollResponseDTO, Poll.class);
    }
}
