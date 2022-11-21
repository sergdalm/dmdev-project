package com.sergdalm.dto;

import com.sergdalm.entity.Gender;
import com.sergdalm.entity.Role;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
@FieldNameConstants
public class UserCreateEditDto {

    @Email
    String email;

    String mobilePhoneNumber;
    Role role;

    @NotEmpty
    @NotBlank
    String password;

    @NotEmpty
    @NotBlank
    String firstName;

    @NotEmpty
    @NotBlank
    String lastName;

    Gender gender;

    @Past
    LocalDate birthday;

    LocalDateTime registeredAt;

    String description;
}
