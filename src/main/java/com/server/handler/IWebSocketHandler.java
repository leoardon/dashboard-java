package com.server.handler;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.socket.WebSocketHandler;

public interface IWebSocketHandler extends WebSocketHandler {

    public MediaType getMediaType();

}