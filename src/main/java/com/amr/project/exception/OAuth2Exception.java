package com.amr.project.exception;

import lombok.Getter;

@Getter
public class OAuth2Exception extends ApiException{

    private final ErrorMessage errorMessage;

    public OAuth2Exception(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
