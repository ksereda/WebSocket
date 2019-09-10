package com.example.displayMessageservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public Mono<String> home() {
        return Mono.just("mainPage");
    }

}
