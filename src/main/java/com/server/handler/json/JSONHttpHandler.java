package com.server.handler.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.server.handler.IHttpHandler;
import com.server.model.WebRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
public class JSONHttpHandler implements IHttpHandler {

	private static final Logger logger = Logger.getLogger(JSONHttpHandler.class.getName());
	private final static ObjectMapper mapper = new ObjectMapper();

	private final MediaType mediaType;

	public JSONHttpHandler() {
		this.mediaType = MediaType.APPLICATION_JSON;
	}

	/**
	 * @return the mediaType
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	@Override
	public Mono<ServerResponse> handle(ServerRequest request) {
		WebRequest req = this.parse(request);
		return this.buildResponse(req);
	}

	private Mono<ServerResponse> buildResponse(WebRequest request) {
		logger.info(request.toString());
		return ServerResponse.ok().body(Mono.just(request.toString()), String.class);
	}

	private WebRequest parse(ServerRequest request) {
		String source = request.pathVariable("source");
		Map<String, Object> params = this.parseParams(request);
		return new WebRequest.WebRequestBuilder().withRandomId().withFormat("json").withSource(source)
				.withParams(params).build();
	}

	private Map<String, Object> parseParams(ServerRequest request) {
		if(request.method().equals(HttpMethod.GET)){
			return this.parseParamsURL(request.pathVariable("params"));
		}
		return null;
	}

	private Map<String, Object> parseParamsURL(String params) {
		params = params.replaceAll("^/+", "").replaceAll("/+$", "");
		if (params.isEmpty()) {
			return new HashMap<String, Object>();
		}
		try {
			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
			return mapper.readValue(params, typeRef);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
