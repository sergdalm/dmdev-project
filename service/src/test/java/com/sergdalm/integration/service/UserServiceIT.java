package com.sergdalm.integration.service;


import com.sergdalm.EntityUtil;
import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.dto.UserReadDto;
import com.sergdalm.dto.UserWithInfoDto;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.Role;
import com.sergdalm.integration.IntegrationTestBase;
import com.sergdalm.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AllArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private static final Integer USER_1_ID = 1;
    private static final UserCreateEditDto USER_CREATE_EDIT_DTO = UserCreateEditDto.builder()
            .role(Role.SPECIALIST)
            .email("igor11@gmail.com")
            .mobilePhoneNumber("+7(911)749-94-28")
            .rowPassword("1111")
            .build();
    private static final UserCreateEditDto NEW_USER_DTO = UserCreateEditDto.builder()
            .email("tamara@gmail.com")
            .rowPassword("nh89hec")
            .mobilePhoneNumber("+7(911)749-29-81")
            .role(Role.CLIENT)
            .firstName("Tamara")
            .lastName("Kim")
            .birthday(LocalDate.of(1994, 5, 7))
            .gender(Gender.FEMALE)
            .build();

    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(6);
    }

    @Test
    void findById() {
        Optional<UserWithInfoDto> maybeUser = userService.findById(EntityUtil.getSpecialistDmitry().getId());
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user ->
                assertEquals(EntityUtil.getSpecialistDmitry().getEmail(), user.getEmail()));
    }

    @Test
    void create() {
        UserWithInfoDto actualResult = userService.create(NEW_USER_DTO);

        assertEquals(NEW_USER_DTO.getEmail(), actualResult.getEmail());
        assertEquals(NEW_USER_DTO.getMobilePhoneNumber(), actualResult.getMobilePhoneNumber());
        assertSame(NEW_USER_DTO.getRole(), actualResult.getRole());
        assertSame(NEW_USER_DTO.getEmail(), actualResult.getEmail());
        assertSame(NEW_USER_DTO.getFirstName(), actualResult.getFirstName());
        assertSame(NEW_USER_DTO.getLastName(), actualResult.getLastName());
        assertSame(NEW_USER_DTO.getBirthday(), actualResult.getBirthday());
        assertSame(NEW_USER_DTO.getGender(), actualResult.getGender());
    }

    @Test
    void update() {
        Optional<UserWithInfoDto> actualResult = userService.update(USER_1_ID, USER_CREATE_EDIT_DTO);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(USER_CREATE_EDIT_DTO.getEmail(), user.getEmail());
            assertEquals(USER_CREATE_EDIT_DTO.getMobilePhoneNumber(), user.getMobilePhoneNumber());
            assertSame(USER_CREATE_EDIT_DTO.getRole(), user.getRole());
        });
    }

    @Test
    void delete() {
        assertTrue(userService.delete(USER_1_ID));
        assertFalse(userService.delete(-135));
    }
}
