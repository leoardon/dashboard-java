package com.server;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebReactiveApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(WebReactiveApplication.class, args);
	}

}
