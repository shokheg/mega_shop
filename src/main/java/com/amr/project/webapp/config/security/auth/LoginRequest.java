package com.amr.project.webapp.config.security.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    private String username;
    private String password;

}
