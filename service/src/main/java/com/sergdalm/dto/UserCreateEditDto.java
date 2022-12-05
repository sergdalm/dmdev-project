package com.sergdalm.dto;

import com.sergdalm.entity.Gender;
import com.sergdalm.entity.Role;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

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
    String rowPassword;

    @NotEmpty
    @NotBlank
    String firstName;

    @NotEmpty
    @NotBlank
    String lastName;

    Gender gender;

    @Past
    LocalDate birthday;

    String description;

    MultipartFile image;
}
