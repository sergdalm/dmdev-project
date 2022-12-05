package com.sergdalm.mapper;

import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.entity.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
public class UserCreateEditToUserInfoMapper implements CreateEditMapper<UserInfo, UserCreateEditDto> {

    @Override
    public UserInfo mapToEntity(UserCreateEditDto dto) {
        UserInfo userInfo = UserInfo.builder()
                .gender(dto.getGender())
                .birthday(dto.getBirthday())
                .description(dto.getDescription())
                .registeredAt(LocalDateTime.now())
                .build();
        Optional.ofNullable(dto.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> userInfo.setImage(image.getOriginalFilename()));
        return userInfo;
    }

    @Override
    public UserInfo mapToEntity(UserCreateEditDto dto, UserInfo entity) {
        entity.setGender(dto.getGender());
        entity.setBirthday(dto.getBirthday());
        entity.setDescription(dto.getDescription());
        Optional.ofNullable(dto.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> entity.setImage(image.getOriginalFilename()));
        return entity;
    }
}
