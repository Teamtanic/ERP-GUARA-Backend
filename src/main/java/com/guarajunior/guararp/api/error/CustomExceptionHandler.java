package com.guarajunior.guararp.api.error;

import com.guarajunior.guararp.api.error.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex, ServerWebExchange exchange) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), ex.getStatus(), ex.getInstant(), exchange.getRequest().getPath().toString());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(exceptionResponse, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllException(Exception ex, ServerWebExchange exchange) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Instant.now(), exchange.getRequest().getPath().toString());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
