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

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyWebSocketHandler implements WebSocketHandler {

	@Autowired
	private MongoRepository repository;

	// The handle method of WebSocketHandler takes WebSocketSession and returns Mono to indicate when application handling of the session is complete.
	// The session is handled through two streams, one for inbound and one for outbound messages.

	// We receive the message (email address) from client and save it to the MongoDB server
	// // Then we send the saved email address (which is returned from repository.save() method) to the client.
	@Override
	public Mono<Void> handle(WebSocketSession session) {
		return session.send(
				session.receive().map(email -> new Users(email.getPayloadAsText())).flatMap(repository::save)
						.map(us -> session.textMessage(us.getEmail())));
	}

}
