package com.server.handler;

import java.util.logging.Logger;

import org.springframework.http.MediaType;

public class HandlerFactory {

    private static Logger logger = Logger.getLogger(HandlerFactory.class.getName());

    public static IHttpHandler getHttpHandler(String mediaType){
        if (mediaType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            return new JSONHttpHandler();
        }
        return null;
    }

    public static IWebSocketHandler getWebSocketHandler(String mediaType){
        if (mediaType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            return new JSONWebSocketHandler();
        }
        return null;
    }

}