package com.sergdalm.mapper;

import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateEditMapper implements CreateEditMapper<User, UserCreateEditDto> {

    @Override
    public User mapToEntity(UserCreateEditDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .mobilePhoneNumber(dto.getMobilePhoneNumber())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    @Override
    public User mapToEntity(UserCreateEditDto dto, User entity) {
        entity.setEmail(dto.getEmail());
        entity.setMobilePhoneNumber(dto.getMobilePhoneNumber());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }
}
