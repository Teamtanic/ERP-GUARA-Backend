package com.guarajunior.guararp.api.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
public class ApiException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private final Instant instant = Instant.now();

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
