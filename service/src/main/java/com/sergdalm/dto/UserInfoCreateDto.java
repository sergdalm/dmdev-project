package com.sergdalm.dto;

import com.sergdalm.entity.Gender;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class UserInfoCreateDto {

    String firstName;
    String lastName;
    Gender gender;
    LocalDate birthday;
    LocalDateTime registeredAt;
    String description;
}
