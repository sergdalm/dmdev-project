package com.sergdalm.integration.service;


import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.dto.UserReadDto;
import com.sergdalm.entity.Role;
import com.sergdalm.integration.IntegrationTestBase;
import com.sergdalm.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

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
    private static final UserReadDto userDto1 = UserReadDto.builder()
            .id(1)
            .role(Role.SPECIALIST)
            .email("igor@gmail.com")
            .mobilePhoneNumber("+7(911)749-94-28")
            .build();
    private static final UserCreateEditDto userCreateEditDto = UserCreateEditDto.builder()
            .role(Role.SPECIALIST)
            .email("igor11@gmail.com")
            .mobilePhoneNumber("+7(911)749-94-28")
            .password("1111")
            .build();
    private static final UserCreateEditDto newUserDto = UserCreateEditDto.builder()
            .email("svetlana@gmail.com")
            .password("nh89hec")
            .mobilePhoneNumber("+7(911)749-29-81")
            .role(Role.CLIENT)
            .build();

    private final UserService userService;


    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_1_ID);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user ->
                assertEquals("igor.gmail.com", user.getEmail()));
    }

    @Test
    void create() {

        UserReadDto actualResult = userService.create(newUserDto);

        assertEquals(newUserDto.getEmail(), actualResult.getEmail());
        assertEquals(newUserDto.getMobilePhoneNumber(), actualResult.getMobilePhoneNumber());
        assertSame(newUserDto.getRole(), actualResult.getRole());
    }

    @Test
    void update() {
        Optional<UserReadDto> actualResult = userService.update(USER_1_ID, userCreateEditDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(userCreateEditDto.getEmail(), user.getEmail());
            assertEquals(userCreateEditDto.getMobilePhoneNumber(), user.getMobilePhoneNumber());
            assertSame(userCreateEditDto.getRole(), user.getRole());
        });
    }

    @Test
    void delete() {
        assertTrue(userService.delete(USER_1_ID));
        assertFalse(userService.delete(-135));
    }
}
