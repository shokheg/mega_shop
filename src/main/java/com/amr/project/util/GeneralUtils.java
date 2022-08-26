package com.amr.project.utils;

import com.amr.project.model.enums.Roles;
import com.amr.project.model.enums.Social;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class GeneralUtils {
    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Roles roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : Roles.values()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }

    public static Social toSocialProvider(String providerId) {
        for (Social social : Social.values()) {
            if (social.getProviderType().equals(providerId)) {
                return social;
            }
        }
        return Social.LOCAL;
    }
}
