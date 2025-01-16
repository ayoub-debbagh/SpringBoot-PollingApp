package com.polling.polling_api.mappers;

public interface Mapper<ENTITY, DTO> {

    DTO mapToDto(ENTITY a);

    ENTITY mapFromDto(DTO b);

}
