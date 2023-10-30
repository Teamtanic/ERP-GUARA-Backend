package com.guarajunior.guararp.api.error.exception;

import org.springframework.http.HttpStatus;

public class InsufficientProductException extends ApiException {
	private static final String DEFAULT_MESSAGE = "Produtos insuficientes no estoque";

	public InsufficientProductException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
	public InsufficientProductException() {
        super(DEFAULT_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
