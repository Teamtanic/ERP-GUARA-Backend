package com.guarajunior.guararp.api.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private HttpStatusCode status;
    private String error;
    private Timestamp timestamp;
    private String path;
    private Map<String, String> details;
}
