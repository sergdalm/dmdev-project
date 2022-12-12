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

    @NotEmpty(message = "Password can't be empty")
    @NotBlank(message = "Password can't be blank")
    String rowPassword;

    @NotEmpty(message = "First name can't be empty")
    @NotBlank(message = "First name can't be blank")
    String firstName;

    @NotEmpty(message = "Last name can't be empty")
    @NotBlank(message = "Last name can't be blank")
    String lastName;

    Gender gender;

    MultipartFile image;

    String mobilePhoneNumber;

    Role role;
    @Past
    LocalDate birthday;

    String description;
}
