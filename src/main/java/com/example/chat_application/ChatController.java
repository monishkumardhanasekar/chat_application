package com.example.chat_application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Chat Application!";
    }
}
