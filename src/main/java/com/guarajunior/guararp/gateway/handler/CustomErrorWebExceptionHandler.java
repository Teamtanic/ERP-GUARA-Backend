package com.guarajunior.guararp.gateway.handler;

import com.guarajunior.guararp.api.error.ExceptionResponse;
import com.guarajunior.guararp.gateway.utils.FilterUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Order(-2)
@Component
public class CustomErrorWebExceptionHandler implements ErrorWebExceptionHandler {
    private final FilterUtils filterUtils = new FilterUtils();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();

        ResponseEntity<?> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, Instant.now(), path));
        return exchange.getResponse().writeWith(Flux.just(filterUtils.convertResponseEntityToDataBuffer(exchange, responseEntity)));
    }

}
