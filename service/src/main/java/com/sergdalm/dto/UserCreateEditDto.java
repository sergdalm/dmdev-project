package com.sergdalm.dto;

import com.sergdalm.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@AllArgsConstructor
@Builder
@FieldNameConstants
public class UserCreateEditDto {

    String email;
    String mobilePhoneNumber;
    Role role;
    String password;
}
