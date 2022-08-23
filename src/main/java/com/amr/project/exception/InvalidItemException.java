package com.amr.project.exception;

import lombok.Getter;

@Getter
public class InvalidItemException extends ApiException {

    private final ErrorMessage errorMessage;

    public InvalidItemException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
