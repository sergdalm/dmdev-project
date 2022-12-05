package com.sergdalm.dto;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Value
public class UserDto extends User {
    Integer id;
    String firstName;

    public UserDto(String username,
                   String password, Collection<? extends GrantedAuthority> authorities,
                   Integer id,
                   String firstName) {
        super(username, password, authorities);
        this.id = id;
        this.firstName = firstName;
    }
}
