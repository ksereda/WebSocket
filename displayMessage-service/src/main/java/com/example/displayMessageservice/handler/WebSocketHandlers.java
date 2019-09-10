package com.example.displayMessageservice.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class WebSocketHandlers implements WebSocketHandler {

    // The handle method of WebSocketHandler takes WebSocketSession and returns Mono to indicate when application handling of the session is complete.
    // The session is handled through two streams, one for inbound and one for outbound messages.

    // We receive the message (email address) from client and save it to the MongoDB server
    // Then we send the saved email address (which is returned from repository.save() method) to the client.
    // The handle() method of WebSocketHandler takes WebSocketSession and returns Mono<Void> to indicate when application handling of the session is complete.
    // The session is handled through two streams, one for inbound and one for outbound messages: Flux<WebSocketMessage> receive() and Mono<Void> send()
    // A WebSocketHandler must compose the inbound and outbound streams into a unified flow and return a Mono<Void> that reflects the completion of that flow.
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // session.receive() method is called, which will actually return a Flux of the websocket messages
        session.send(session.receive()
                // The flux is mapped through the WebsocketMessage::getPayloadAsText method, which will give us the payload message
                .map(WebSocketMessage::getPayloadAsText)
                //  Finally we run it through a map method, turning it into a text message to be sent.
                //  This will result in a Flux, which is passed as an argument to the session.send() method, having it stream to the user.
                .map(stringMessage -> session.textMessage(stringMessage)))
                .subscribe(System.out::println);

        return Mono.empty();
    }

}
