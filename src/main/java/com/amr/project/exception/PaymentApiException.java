package com.amr.project.exception;

/**
 * @author shokhalevich
 */
public class PaymentApiException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public PaymentApiException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
