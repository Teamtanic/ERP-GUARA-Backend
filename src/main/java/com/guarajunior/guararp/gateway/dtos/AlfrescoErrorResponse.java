package com.guarajunior.guararp.gateway.dtos;

import com.guarajunior.guararp.api.error.ExceptionResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class AlfrescoErrorResponse {
    private Error error;

    public ExceptionResponse toExceptionResponse(String path) {
        return new ExceptionResponse(error.briefSummary, HttpStatus.valueOf(error.statusCode), Instant.now(), path);
    }

    @Data
    public static class Error {
        private String errorKey;
        private Integer statusCode;
        private String briefSummary;
        private String stackTrace;
        private String descriptionURL;
        private String logId;
    }
}
