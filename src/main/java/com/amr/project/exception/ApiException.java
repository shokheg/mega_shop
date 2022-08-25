package com.amr.project.exception;

/**
 * @author shokhalevich
 */
public abstract class ApiException extends RuntimeException {

   abstract ErrorMessage getErrorMessage();
}
