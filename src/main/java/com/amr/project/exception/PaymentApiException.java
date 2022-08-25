package com.amr.project.exception;

import lombok.Getter;

/**
 * @author shokhalevich
 */
@Getter
public class PaymentApiException extends ApiException {

    private final ErrorMessage errorMessage;

    public PaymentApiException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }



}
