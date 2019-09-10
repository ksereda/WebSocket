package com.example.websocketserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.Collections;

@Component
@Slf4j
public class MyWebSocketHandler {

    // WebSocketHandlerAdapter: to handle our web socket handshake, upgrade, and other connection details.
    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    WebSocketHandler webSocketHandler() {
        return session ->
                session.send(
                        Flux.interval(Duration.ofSeconds(1))
                                //  Finally we run it through a map method, turning it into a text message to be sent.
                                //  This will result in a Flux, which is passed as an argument to the session.send() method, having it stream to the user.
                                .map(Object::toString)   // this is analog: n -> n.toString()
                                .map(session::textMessage)
                ).and(session.receive()    // session.receive() method is called, which will actually return a Flux of the websocket messages
                        .map(WebSocketMessage::getPayloadAsText)   // The flux is mapped through the WebsocketMessage::getPayloadAsText method, which will give us the payload message
                        .doOnNext(msg -> log.info("Result: " + msg))
                );
    }

    @Bean
    HandlerMapping webSocketURLMapping() {
//        Map<String, MyWebSocketHandler> map = new HashMap<>();
//        map.put("/ws/feed", webSocketHandler());

        // Create an instance of SimpleUrlHandlerMapping, and add Mappings for URL to WebSocketHandler exchanges.
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(Collections.singletonMap("/ws/test", webSocketHandler()));
        mapping.setCorsConfigurations(Collections.singletonMap("*", new CorsConfiguration().applyPermitDefaultValues()));  // for CORS
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return mapping;
    }

}
