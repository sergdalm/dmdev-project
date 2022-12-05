package com.sergdalm.mapper;

import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserCreateEditMapper implements CreateEditMapper<User, UserCreateEditDto> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User mapToEntity(UserCreateEditDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .mobilePhoneNumber(dto.getMobilePhoneNumber())
                .password(passwordEncoder.encode(dto.getRowPassword()))
                .role(dto.getRole())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }

    @Override
    public User mapToEntity(UserCreateEditDto dto, User entity) {
        entity.setEmail(dto.getEmail());
        entity.setMobilePhoneNumber(dto.getMobilePhoneNumber());
        entity.setPassword(passwordEncoder.encode(dto.getRowPassword()));
        entity.setRole(dto.getRole());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return entity;
    }
}
