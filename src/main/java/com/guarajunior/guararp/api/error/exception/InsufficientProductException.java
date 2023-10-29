package com.guarajunior.guararp.api.error.exception;

public class InsufficientProductException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientProductException(String message) {
        super(message);
    }
}
