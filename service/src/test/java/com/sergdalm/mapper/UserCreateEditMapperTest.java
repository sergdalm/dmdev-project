package com.sergdalm.mapper;

import com.sergdalm.DtoUtill;
import com.sergdalm.EntityUtil;
import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.entity.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor
class UserCreateEditMapperTest {

    private final UserCreateEditMapper userCreateEditMapper;

    @Test
    void mapToEntityFromDto() {
        User user = User.builder()
                .email(EntityUtil.getSpecialistDmitry().getEmail())
                .mobilePhoneNumber(EntityUtil.getSpecialistDmitry().getMobilePhoneNumber())
                .password(EntityUtil.getSpecialistDmitry().getPassword())
                .role(EntityUtil.getSpecialistDmitry().getRole())
                .build();

        UserCreateEditDto userCreateEditDto = DtoUtill.NEW_USER_SPECIALIST_DTO;

        User actualResult = userCreateEditMapper.mapToEntity(userCreateEditDto);

        assertEquals(user, actualResult);
        assertEquals(user.getEmail(), actualResult.getEmail());
        assertEquals(user.getMobilePhoneNumber(), actualResult.getMobilePhoneNumber());
        assertEquals(user.getRole(), actualResult.getRole());
        assertEquals(user.getPassword(), actualResult.getPassword());
    }

    @Test
    void mapToEntityFromDtoAndEntity() {
        User previousUser = EntityUtil.getSpecialistDmitry();

        User updatedUser = User.builder()
                .id(previousUser.getId())
                .email(EntityUtil.getSpecialistDmitry().getEmail())
                .mobilePhoneNumber(EntityUtil.getSpecialistDmitry().getMobilePhoneNumber())
                .password(EntityUtil.getSpecialistDmitry().getPassword())
                .role(EntityUtil.getSpecialistDmitry().getRole())
                .build();

        UserCreateEditDto userCreateEditDto = DtoUtill.UPDATED_SPECIALIST_DTO;

        User actualResult = userCreateEditMapper.mapToEntity(userCreateEditDto, previousUser);

        assertEquals(updatedUser, actualResult);
        assertEquals(updatedUser.getEmail(), actualResult.getEmail());
        assertEquals(updatedUser.getId(), actualResult.getId());
        assertEquals(updatedUser.getMobilePhoneNumber(), actualResult.getMobilePhoneNumber());
        assertEquals(updatedUser.getRole(), actualResult.getRole());
        assertEquals(updatedUser.getPassword(), actualResult.getPassword());
    }
}