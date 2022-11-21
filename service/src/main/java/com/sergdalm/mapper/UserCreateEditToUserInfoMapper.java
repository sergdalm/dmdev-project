package com.sergdalm.mapper;

import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.entity.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserCreateEditToUserInfoMapper implements CreateEditMapper<UserInfo, UserCreateEditDto> {

    @Override
    public UserInfo mapToEntity(UserCreateEditDto dto) {
        return UserInfo.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .birthday(dto.getBirthday())
                .description(dto.getDescription())
                .registeredAt(dto.getRegisteredAt())
                .build();
    }

    @Override
    public UserInfo mapToEntity(UserCreateEditDto dto, UserInfo entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setGender(dto.getGender());
        entity.setBirthday(dto.getBirthday());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
