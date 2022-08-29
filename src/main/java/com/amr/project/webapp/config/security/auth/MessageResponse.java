package com.amr.project.webapp.config.security.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
