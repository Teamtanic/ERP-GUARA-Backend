package com.guarajunior.rp.exception;

public class InsufficientProductException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientProductException(String message) {
        super(message);
    }
}
