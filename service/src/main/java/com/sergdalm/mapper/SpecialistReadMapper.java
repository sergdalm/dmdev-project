package com.sergdalm.mapper;

import com.sergdalm.dto.SpecialistDto;
import com.sergdalm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SpecialistReadMapper implements ReadMapper<User, SpecialistDto> {

    @Override
    public SpecialistDto mapToDto(User entity) {
        return SpecialistDto.builder()
                .id(entity.getId())
                .fullName(entity.getFirstName() + " " + entity.getLastName())
                .image(entity.getUserInfo().getImage())
                .description(entity.getUserInfo().getDescription())
                .build();
    }
}
