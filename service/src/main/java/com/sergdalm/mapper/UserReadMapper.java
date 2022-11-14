package com.sergdalm.mapper;

import com.sergdalm.dto.UserReadDto;
import com.sergdalm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements ReadMapper<User, UserReadDto> {

    @Override
    public UserReadDto mapToDto(User entity) {
        return UserReadDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .mobilePhoneNumber(entity.getMobilePhoneNumber())
                .role(entity.getRole())
                .build();
    }
}
