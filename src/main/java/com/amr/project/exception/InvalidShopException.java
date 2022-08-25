package com.amr.project.exception;

import io.swagger.annotations.Api;
import lombok.Getter;

@Getter
public class InvalidShopException extends ApiException {

    private final ErrorMessage errorMessage;

    public InvalidShopException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
