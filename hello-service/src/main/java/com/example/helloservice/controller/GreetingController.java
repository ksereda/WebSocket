package com.example.helloservice.controller;

import com.example.helloservice.model.Greeting;
import com.example.helloservice.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * The @MessageMapping annotation ensures that if a message is sent to destination "/hello", then the greeting() method is called.
 *
 * Internally, the implementation of the method simulates a processing delay by causing the thread to sleep for 1 second.
 * This is to demonstrate that after the client sends a message, the server can take as long as it needs to process the message asynchronously.
 * The client may continue with whatever work it needs to do without waiting on the response.
 *
 * After the 1 second delay, the greeting() method creates a Greeting object and returns it.
 * The return value is broadcast to all subscribers to "/topic/greetings" as specified in the @SendTo annotation.
 * Note that the name from the input message is sanitized since in this case it will be echoed back and re-rendered in the browser DOM on the client side.
 */

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
