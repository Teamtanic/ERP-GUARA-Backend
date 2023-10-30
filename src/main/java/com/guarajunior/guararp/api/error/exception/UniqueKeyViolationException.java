package com.guarajunior.guararp.api.error.exception;

import org.springframework.http.HttpStatus;

public class UniqueKeyViolationException extends ApiException {
    private static final String DEFAULT_MESSAGE = "Violação de chave única";

    public UniqueKeyViolationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
    public UniqueKeyViolationException() {
        super(DEFAULT_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
