package com.guarajunior.guararp.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guarajunior.guararp.api.error.ExceptionResponse;
import com.guarajunior.guararp.gateway.dtos.AlfrescoErrorResponse;
import com.guarajunior.guararp.gateway.utils.FilterUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalWebFilter implements WebFilter {
    private final ObjectMapper objectMapper;
    private final FilterUtils filterUtils;

    public GlobalWebFilter(ObjectMapper objectMapper, FilterUtils filterUtils) {
        this.objectMapper = objectMapper;
        this.filterUtils = filterUtils;
    }

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("GLOBAL FILTER");
        ServerHttpRequest decoratedRequest = filterUtils.getDecoratedRequest(exchange.getRequest());
        ServerHttpResponseDecorator decoratedResponse = filterUtils.getDecoratedResponse(exchange, this::editBody);

        return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build());
    }

    private String editBody(String body, String path) {
        try {
            AlfrescoErrorResponse alfrescoErrorResponse = objectMapper.readValue(body, AlfrescoErrorResponse.class);
            ExceptionResponse exceptionResponse = alfrescoErrorResponse.toExceptionResponse(path);

            log.error(objectMapper.writeValueAsString(exceptionResponse));
            return objectMapper.writeValueAsString(exceptionResponse);
        } catch (JsonProcessingException ignored) {
            return body;
        }
    }
}