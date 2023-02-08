package com.example.forum.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_USER,
    ROLE_OPERATOR,
    ROLE_ADMIN,
    ROLE_BANNED;

    @Override
    public String getAuthority() {
        return name();
    }
}