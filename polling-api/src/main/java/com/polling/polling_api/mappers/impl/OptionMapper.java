package com.polling.polling_api.mappers.impl;

import com.polling.polling_api.dtos.OptionDTO;
import com.polling.polling_api.mappers.Mapper;
import com.polling.polling_api.models.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptionMapper implements Mapper<Option, OptionDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OptionDTO mapToDto(Option option) {
        return modelMapper.map(option, OptionDTO.class);
    }

    @Override
    public Option mapFromDto(OptionDTO optionDTO) {
        return modelMapper.map(optionDTO, Option.class);
    }
}
