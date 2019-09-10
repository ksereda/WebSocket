package com.example.displayMessageservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import java.util.Collections;

@Configuration
public class WebSocketConfiguration {

    @Autowired
    private WebSocketHandler webSocketHandler;

    // WebSocketHandlerAdapter: to handle our web socket handshake, upgrade, and other connection details.
    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    HandlerMapping webSocketURLMapping() {
        // Create an instance of SimpleUrlHandlerMapping, and add Mappings for URL to WebSocketHandler exchanges.
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(Collections.singletonMap("/message", webSocketHandler));
        mapping.setCorsConfigurations(Collections.singletonMap("*", new CorsConfiguration().applyPermitDefaultValues()));  // for CORS
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return mapping;
    }

}
