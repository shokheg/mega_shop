package com.amr.project.model.enums;

import org.springframework.security.core.GrantedAuthority;


public enum Roles implements GrantedAuthority{
    USER,
    MODERATOR,
    ADMIN,
    ANONYMOUS;

    @Override
    public String getAuthority() {
        return name();
    }
}
