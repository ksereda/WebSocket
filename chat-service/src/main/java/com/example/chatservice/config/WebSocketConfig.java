package com.example.chatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker  // enable WebSocket server
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // registering a websocket endpoint that the clients will use to connect to websocket server
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();  // SockJS is used to enable fallback options for browsers that don’t support websocket
    }

    // configuring a message broker that will be used to route messages from one client to another
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");  // the messages whose destination starts with “/app” should be routed to message-handling methods
        registry.enableSimpleBroker("/topic");  // the messages whose destination starts with “/topic” should be routed to the message broker
                                                                 // Message broker broadcasts messages to all the connected clients who are subscribed to a particular topic
    }
}
