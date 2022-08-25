package com.amr.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ApiException.class})
    protected ResponseEntity<Object> handleApiException(ApiException ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }
}

