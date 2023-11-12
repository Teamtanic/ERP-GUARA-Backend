package com.guarajunior.guararp.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FilterUtils {

    private final ObjectMapper objectMapper = objectMapper();

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public DataBuffer convertResponseEntityToDataBuffer(ServerWebExchange exchange, ResponseEntity<?> responseEntity) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(responseEntity.getStatusCode());

        byte[] responseBodyBytes;

        try {
            responseBodyBytes = objectMapper.writeValueAsBytes(responseEntity.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.getHeaders().setContentType(responseEntity.getHeaders().getContentType());

        DataBufferFactory dataBufferFactory = response.bufferFactory();

        return dataBufferFactory.wrap(responseBodyBytes);
    }

    public ServerHttpResponseDecorator getDecoratedResponse(ServerWebExchange exchange, EditBody editBody) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();

        return new ServerHttpResponseDecorator(response) {
            @Override
            @NonNull
            public Mono<Void> writeWith(@NonNull final Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux<? extends DataBuffer> fluxBody) {

                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                        DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
                        byte[] content = new byte[joinedBuffers.readableByteCount()];
                        joinedBuffers.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);

                        return response.bufferFactory().wrap(editBody.execute(responseBody, request.getPath().toString()).getBytes());

                    }).switchIfEmpty(Flux.defer(() -> {

                        System.out.println("Write to database here");
                        return Flux.just();
                    }))).onErrorResume(err -> Mono.empty());

                } else {
                    System.out.println("2000000000");
                }
                return super.writeWith(body);
            }
        };
    }

    public ServerHttpRequest getDecoratedRequest(ServerHttpRequest request) {

        return new ServerHttpRequestDecorator(request) {
            @Override
            @NonNull
            public Flux<DataBuffer> getBody() {

                return super.getBody().publishOn(Schedulers.boundedElastic()).doOnNext(dataBuffer -> {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                        String body = baos.toString(StandardCharsets.UTF_8);

                        log.info("for requestId: {}, request body :{}", request.getId(), body);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });
            }
        };
    }

    public interface EditBody {
        String execute(String responseBody, String path);
    }
}
