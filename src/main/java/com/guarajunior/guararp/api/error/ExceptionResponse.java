package com.guarajunior.guararp.api.error;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ExceptionResponse(String message, HttpStatus status, Instant instant, String path) {}