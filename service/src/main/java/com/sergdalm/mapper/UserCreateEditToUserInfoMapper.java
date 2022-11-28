package com.sergdalm.mapper;

import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.entity.UserInfo;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class UserCreateEditToUserInfoMapper implements CreateEditMapper<UserInfo, UserCreateEditDto> {

    @Override
    public UserInfo mapToEntity(UserCreateEditDto dto) {
        return UserInfo.builder()
                .gender(dto.getGender())
                .birthday(dto.getBirthday())
                .description(dto.getDescription())
                .registeredAt(LocalDateTime.now())
                .build();
    }

    @Override
    public UserInfo mapToEntity(UserCreateEditDto dto, UserInfo entity) {
        entity.setGender(dto.getGender());
        entity.setBirthday(dto.getBirthday());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
