package com.sergdalm.dto;

import com.sergdalm.entity.Gender;
import com.sergdalm.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class UserWithInfoDto {

    Integer id;
    String email;
    Role role;
    String mobilePhoneNumber;
    String firstName;
    String lastName;
    Gender gender;
    LocalDate birthday;
    LocalDateTime registeredAt;
    String description;
}
