package com.sergdalm.mapper;

import com.sergdalm.dto.ServiceCreateDto;
import com.sergdalm.entity.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceCreateMapper implements CreateEditMapper<Service, ServiceCreateDto> {

    @Override
    public Service mapToEntity(ServiceCreateDto dto) {
        return Service.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public Service mapToEntity(ServiceCreateDto dto, Service entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
