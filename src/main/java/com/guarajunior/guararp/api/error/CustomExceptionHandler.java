package com.guarajunior.guararp.api.error;

import com.guarajunior.guararp.api.error.exception.ApiException;
import com.guarajunior.guararp.api.error.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler extends CustomResponseEntityExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
        return apiHandle(ex, request);
    }
}
