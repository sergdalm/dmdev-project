package com.sergdalm.mapper;

import com.sergdalm.EntityUtil;
import com.sergdalm.dto.UserReadDto;
import com.sergdalm.entity.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
class UserReadMapperTest {

    private final UserReadMapper userReadMapper;

    @Test
    void mapToDto() {
        User user = EntityUtil.getSpecialistNatali();
        UserReadDto userWithInfoDto = UserReadDto.builder()
                .id(user.getId())
                .mobilePhoneNumber(user.getMobilePhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        UserReadDto actualResult = userReadMapper.mapToDto(user);

        assertEquals(userWithInfoDto, actualResult);
    }
}