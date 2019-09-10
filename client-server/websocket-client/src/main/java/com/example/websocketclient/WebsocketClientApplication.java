package com.example.websocketclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class WebsocketClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketClientApplication.class, args);
	}

	@Value("${app.client.url:http://localhost:8080/ws/test}")
	private String serverURI;


	Mono<Void> wsConnectNetty() {
		WebSocketClient client = new ReactorNettyWebSocketClient();   // ReactorNettyWebSocketClient is a WebSocketClient implementation for use with Reactor Netty
		return client.execute(
				URI.create(serverURI),   // a client connection to the WebSocket server through the URL establishes a session as soon as it connects to the server
				session -> session
						// The receive() method receives a stream of incoming messages, which are subsequently converted to strings
						.receive()
						.map(WebSocketMessage::getPayloadAsText)
						.take(8)
						.doOnNext(number ->
								log.info("Session id: " + session.getId() + " execute: " + number)
						)
						.flatMap(txt ->
								session.send(
										Mono.just(session.textMessage(txt))
								)
						)
						.doOnSubscribe(subscriber ->
								log.info("Session id: " + session.getId() + " open connection")
						)
						.doFinally(signalType -> {
							session.close();
							log.info("Session id: " + session.getId() + " close connection");
						})
						.then()
		);
	}

	// for test
	@Bean
	ApplicationRunner appRunner() {
		return args -> {
			final CountDownLatch latch = new CountDownLatch(2);
			Flux.merge(
					Flux.range(0, 2)
							.subscribeOn(Schedulers.single())
							.map(n -> wsConnectNetty()
									.doOnTerminate(latch::countDown))
							.parallel()
			)
					.subscribe();

			latch.await(20, TimeUnit.SECONDS);
		};
	}



}
