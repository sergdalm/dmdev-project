package com.sergdalm.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    SPECIALIST,
    CLIENT,
    ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
