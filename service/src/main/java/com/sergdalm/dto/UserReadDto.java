package com.sergdalm.dto;

import com.sergdalm.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class UserReadDto {

    Integer id;
    String email;
    String mobilePhoneNumber;
    Role role;
}
