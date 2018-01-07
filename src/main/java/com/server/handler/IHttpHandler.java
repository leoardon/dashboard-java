package com.server.handler;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface IHttpHandler {

    public Mono<ServerResponse> handle(ServerRequest request);

    public MediaType getMediaType();

}