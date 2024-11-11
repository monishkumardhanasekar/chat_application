package com.example.chat_application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull; // Import NonNull annotation
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry config) { // Add NonNull annotation
        config.enableSimpleBroker("/topic"); // Enables a broker to broadcast messages to topics
        config.setApplicationDestinationPrefixes("/app"); // Prefix for message mappings
    }

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat").withSockJS(); // WebSocket endpoint
    }
}
