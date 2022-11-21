package com.sergdalm.mapper;

import com.sergdalm.EntityUtil;
import com.sergdalm.dto.UserWithInfoDto;
import com.sergdalm.entity.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor
class UserWithInfoMapperTest {

    private final UserWithInfoMapper userWithInfoMapper;

    @Test
    void mapToDto() {
        User user = EntityUtil.getSpecialistNatali();
        UserWithInfoDto userWithInfoDto = UserWithInfoDto.builder()
                .id(user.getId())
                .mobilePhoneNumber(user.getMobilePhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .firstName(user.getUserInfo().getFirstName())
                .lastName(user.getUserInfo().getLastName())
                .description(user.getUserInfo().getDescription())
                .registeredAt(user.getUserInfo().getRegisteredAt())
                .birthday(user.getUserInfo().getBirthday())
                .build();

        UserWithInfoDto actualResult = userWithInfoMapper.mapToDto(user);

        assertEquals(userWithInfoDto, actualResult);
    }
}