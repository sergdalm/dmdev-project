package com.sergdalm.mapper;

import com.sergdalm.DtoUtill;
import com.sergdalm.EntityUtil;
import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.entity.UserInfo;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor
class UserCreateEditToUserInfoMapperTest {

    private final UserCreateEditToUserInfoMapper userCreateEditToUserInfoMapper;

    @Test
    void mapToEntityFromDto() {
        UserCreateEditDto userCreateEditDto = DtoUtill.NEW_USER_SPECIALIST_DTO;
        UserInfo userInfo = UserInfo.builder()
                .gender(userCreateEditDto.getGender())
                .birthday(userCreateEditDto.getBirthday())
                .description(userCreateEditDto.getDescription())
                .build();

        UserInfo actualResult = userCreateEditToUserInfoMapper.mapToEntity(userCreateEditDto);

        assertEquals(userInfo, actualResult);
        assertEquals(userInfo.getBirthday(), actualResult.getBirthday());
        assertEquals(userInfo.getGender(), actualResult.getGender());
        assertEquals(userInfo.getDescription(), actualResult.getDescription());
    }

    @Test
    void mapToEntityFromDtoAndEntity() {
        UserCreateEditDto userCreateEditDto = DtoUtill.UPDATED_SPECIALIST_DTO;
        UserInfo previousUserInfo = EntityUtil.getSpecialistDmitryUserInfo();

        UserInfo actualResult = userCreateEditToUserInfoMapper.mapToEntity(userCreateEditDto);

        assertEquals(previousUserInfo.getId(), actualResult.getId());
        assertEquals(userCreateEditDto.getBirthday(), actualResult.getBirthday());
        assertEquals(userCreateEditDto.getGender(), actualResult.getGender());
        assertEquals(userCreateEditDto.getDescription(), actualResult.getDescription());
    }
}