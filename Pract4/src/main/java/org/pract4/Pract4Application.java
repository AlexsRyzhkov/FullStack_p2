package org.pract4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import java.util.concurrent.CancellationException;

@SpringBootApplication
public class Pract4Application {

	public static void main(String[] args) {
		SpringApplication.run(Pract4Application.class, args);
	}
}