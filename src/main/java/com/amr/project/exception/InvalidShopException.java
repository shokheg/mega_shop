package com.amr.project.exception;

public class InvalidShopException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public InvalidShopException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
