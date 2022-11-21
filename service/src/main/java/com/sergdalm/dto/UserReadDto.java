package com.sergdalm.dto;

import com.sergdalm.entity.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {

    Integer id;
    String email;
    String mobilePhoneNumber;
    Role role;
}
