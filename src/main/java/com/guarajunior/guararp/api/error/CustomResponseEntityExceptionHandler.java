package com.guarajunior.guararp.api.error;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.guarajunior.guararp.api.error.exception.ApiException;
import com.guarajunior.guararp.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//    public static final String MSG_VALIDATION_ERROR = "Campo '%s': %s";
    public static final String MSG_VALUE_EXPECTED_TYPE = "Era esperado um valor de tipo %s na linha %d, coluna %d";


    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Objects.requireNonNull(fieldError.getDefaultMessage())
                ));

        ErrorResponse response = ErrorResponse.builder().status(status).message("Argumentos inválidos").timestamp(Timestamp.valueOf(LocalDateTime.now())).details(validationErrors).build();
        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof MismatchedInputException) {
            return handleMismatchedInput((MismatchedInputException) rootCause, headers, status, request);
        }

        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    private ResponseEntity<Object> handleMismatchedInput(MismatchedInputException ex, HttpHeaders headers,
                                                         HttpStatusCode status, WebRequest request) {
        Integer lineNumber = ex.getLocation().getLineNr();
        Integer columnNumber = ex.getLocation().getColumnNr();
        String expectedType = ex.getTargetType().getSimpleName();
        String message = String.format(MSG_VALUE_EXPECTED_TYPE, expectedType, lineNumber, columnNumber);
        ErrorResponse response = ErrorResponse.builder().status(status).message(ex.getMessage()).timestamp(Timestamp.valueOf(LocalDateTime.now())).build();

        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), headers, status, webRequest);
    }

    private ErrorResponse buildDefaultErrorResponse(Exception ex) {
        return ErrorResponse.builder().message(ExceptionUtils.getExceptionRootCauseMessage(ex)).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    public ResponseEntity<Object> defaultHandle(Exception ex, WebRequest request, HttpStatus httpStatus) {
        return handleExceptionInternal(ex, buildDefaultErrorResponse(ex), new HttpHeaders(), httpStatus, request);
    }

    public ResponseEntity<Object> apiHandle(ApiException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder().message(ex.getMessage()).status(ex.getStatus()).build();
        return handleExceptionInternal(ex, response, new HttpHeaders(), ex.getStatus(), request);
    }
}