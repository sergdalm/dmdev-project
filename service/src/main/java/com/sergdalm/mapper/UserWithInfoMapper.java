package com.sergdalm.mapper;

import com.sergdalm.dto.UserWithInfoDto;
import com.sergdalm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserWithInfoMapper implements ReadMapper<User, UserWithInfoDto> {

    @Override
    public UserWithInfoDto mapToDto(User user) {
        return UserWithInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .mobilePhoneNumber(user.getMobilePhoneNumber())
                .firstName(user.getUserInfo().getFirstName())
                .lastName(user.getUserInfo().getLastName())
                .gender(user.getUserInfo().getGender())
                .birthday(user.getUserInfo().getBirthday())
                .description(user.getUserInfo().getDescription())
                .build();
    }
}
