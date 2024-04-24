package com.example.be3rdproject.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //WebSocket을 위한 메시지 브로커를 구성함
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
    // WebSocketMessageBrokerConfigurer 인터페이스를 구현함으로써 WebSocket 메시지 브로커 설정

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat") // WebSocket을 통해 연결할 수 있는 엔드포인트를 "/chat"으로 설정
                .setAllowedOriginPatterns("*"); // 모든 도메인에서의 접근을 허용
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        // 메시지 브로커가 구독 관리를 위해 사용할 prefix를 "/topic"으로 설정 클라이언트가 이 prefix를 사용하여 메시지를 구독하면 메시지 브로커가 해당 메시지를 구독자에게 전달
        registry.setApplicationDestinationPrefixes("/app");
        // 클라이언트에서 메시지를 발송할 때 사용할 prefix를 "/app"으로 설정
    }
}