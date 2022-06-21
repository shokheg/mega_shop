package com.amr.project.exception;

public class InvalidItemException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public InvalidItemException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
