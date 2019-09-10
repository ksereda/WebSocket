package com.example.subscribeUsapp.handler;

import com.example.subscribeUsapp.entity.Users;
import com.example.subscribeUsapp.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * WebSocketHandler is needed to handle a socket session in our application.
 * We can override its webSocketHandlerMapping () method, which will be responsible for displaying between requests and handler objects
 *
 * Our MyWebSocketHandler class will be responsible for managing the server-side WebSocket session
 * the handle () method is needed to send a message to the WebSocket client
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyWebSocketHandler implements WebSocketHandler {

	@Autowired
	private MongoRepository repository;

	// The handle method of WebSocketHandler takes WebSocketSession and returns Mono to indicate when application handling of the session is complete.
	// The session is handled through two streams, one for inbound and one for outbound messages.

	// We receive the message (email address) from client and save it to the MongoDB server
	// Then we send the saved email address (which is returned from repository.save() method) to the client.
	// The handle() method of WebSocketHandler takes WebSocketSession and returns Mono<Void> to indicate when application handling of the session is complete.
	// The session is handled through two streams, one for inbound and one for outbound messages: Flux<WebSocketMessage> receive() and Mono<Void> send()
	// A WebSocketHandler must compose the inbound and outbound streams into a unified flow and return a Mono<Void> that reflects the completion of that flow.
	@Override
	public Mono<Void> handle(WebSocketSession session) {
		return session
				.send(session.receive()  // session.receive() method is called, which will actually return a Flux of the websocket messages
						.map(email -> new Users(email.getPayloadAsText())).flatMap(repository::save)
						// Finally we run it through a map method, turning it into a text message to be sent.
						// This will result in a Flux, which is passed as an argument to the session.send() method, having it stream to the user.
						// there is a combination of incoming and outgoing flow
						.map(us -> session.textMessage(us.getEmail())));
	}

}
