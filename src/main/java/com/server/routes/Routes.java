package com.server.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.HashMap;
import java.util.Map;

import com.server.handler.HandlerFactory;
import com.server.handler.IHttpHandler;
import com.server.handler.IWebSocketHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

@Configuration
public class Routes {
	
	@Autowired
	private Environment environment;
	
	@Bean
	public RouterFunction<?> routerFunction() {
		IHttpHandler httpHandler = dashboardHttpHandler();
		return route(path(getURI("{source}/{*params}")).and(accept(httpHandler.getMediaType())), httpHandler::handle);
	}

	@Bean
	public HandlerMapping webSocketMapping() {
		Map<String, IWebSocketHandler> map = new HashMap<>();
		IWebSocketHandler webSocketHandler = dashboardWebSocketHandler();
		map.put(getURI("ws"), webSocketHandler);

		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setUrlMap(map);
		mapping.setOrder(1);
		return mapping;
	}

	@Bean
	public IHttpHandler dashboardHttpHandler(){
		String mediaType = environment.getProperty("api.mediaType");
		return HandlerFactory.getHttpHandler(mediaType);
	}

	@Bean
	public IWebSocketHandler dashboardWebSocketHandler(){
		String mediaType = environment.getProperty("api.mediaType");
		return HandlerFactory.getWebSocketHandler(mediaType);
	}

	private String getURI(String uri) {
		return getBaseURI() + uri;
	}

	private String getBaseURI() {
		String version = environment.getProperty("api.version");
		return "/api/" + version + "/";
	}

}
