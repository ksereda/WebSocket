package com.example.displaylogoapp;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import java.io.IOException;

/**
 * In order to use the WebSocketHandler, it must be registered in Spring’s WebSocketHandlerRegistry (see WebSocketConfiguration class)
 */

public class WebSocketHandler extends AbstractWebSocketHandler {

    // for text
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message Received");
        session.sendMessage(message);
    }

    // for file
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        session.sendMessage(message);
    }
}
