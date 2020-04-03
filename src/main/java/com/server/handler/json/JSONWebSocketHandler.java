package com.server.handler.json;

import java.io.IOException;
import java.util.logging.Logger;
import com.server.handler.IWebSocketHandler;
import com.server.model.WebRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketMessage.Type;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;;

public class JSONWebSocketHandler implements IWebSocketHandler {
	
	private final static Logger logger = Logger.getLogger(JSONWebSocketHandler.class.getName());
	private final static ObjectMapper mapper = new ObjectMapper();

	private final MediaType mediaType;

	public JSONWebSocketHandler() {
		this.mediaType = MediaType.APPLICATION_JSON;
	}

	@Override
	public MediaType getMediaType() {
		return this.mediaType;
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		return session.send(session.receive().map(WebSocketMessage::getPayloadAsText).flatMap(this::parse)
				.flatMap(r -> this.buildResponse(session.bufferFactory(), r)));
	}

	protected Flux<WebRequest> parse(String input) {
		try {
			return Flux.just(mapper.readValue(input, WebRequest.class));
		} catch (IOException e) {
			e.printStackTrace();
			return Flux.empty();
		}
	}

	protected Flux<WebSocketMessage> buildResponse(DataBufferFactory bufferFactory, WebRequest request) {
		logger.finer("Received: " + request.toString());
		DataBuffer buffer;
		try {
			buffer = bufferFactory.wrap(mapper.writeValueAsBytes(request));
			return Flux.just(new WebSocketMessage(Type.TEXT, buffer));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Flux.empty();
		}		
	}

}