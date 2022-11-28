package com.sergdalm.mapper;

import com.sergdalm.dto.ServiceReadDto;
import com.sergdalm.entity.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceReadMapper implements ReadMapper<Service, ServiceReadDto> {

    @Override
    public ServiceReadDto mapToDto(Service entity) {
        return ServiceReadDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
