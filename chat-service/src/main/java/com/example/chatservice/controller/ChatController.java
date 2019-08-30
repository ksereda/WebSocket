package com.example.chatservice.controller;

import com.example.chatservice.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * These methods will be responsible for receiving messages from one client and then broadcasting it to others
 *
 * In the configuration, we indicated that all messages from clients directed to the address starting with "/app" will be redirected to the appropriate methods (methods that are annotated as @MessageMapping)
 */

@Controller
public class ChatController {

    // A message with destination "/app/chat.sendMessage" will be routed to the this method
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    // A message with destination "/app/chat.addUser" will be routed to the this method.
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
