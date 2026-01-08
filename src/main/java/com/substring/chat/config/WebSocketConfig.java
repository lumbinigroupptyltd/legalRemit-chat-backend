package com.substring.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${ws.front.end.base.url:http://localhost:5173}")
    private String wsFrontEBaseUrl;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Keep SockJS endpoint for backward compatibility
        registry.addEndpoint("/chat")
                .setAllowedOrigins(wsFrontEBaseUrl)
                .withSockJS();

        // Add plain WebSocket endpoint for @stomp/stompjs
        registry.addEndpoint("/ws")
                .setAllowedOrigins(wsFrontEBaseUrl);
    }
}