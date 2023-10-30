package com.guarajunior.guararp.api.error.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApiException {
    private static final String DEFAULT_MESSAGE = "Entidade não encontrada";

	public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }
}