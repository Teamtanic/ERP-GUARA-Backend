package com.guarajunior.guararp.api.error.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApiException  {
    private static final String DEFAULT_MESSAGE = "Não autorizado";

	public InvalidTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
	public InvalidTokenException() {
        super(DEFAULT_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}
