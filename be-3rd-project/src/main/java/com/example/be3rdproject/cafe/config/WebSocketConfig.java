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
        registry.addEndpoint("/ws")     // WebSocket을 통해 연결할 수 있는 엔드포인트를 "/ws"으로 설정
                .setAllowedOriginPatterns("*"); // 모든 도메인에서의 접근을 허용
        // 주소 : ws://localhost:8080/ws
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/send");       //클라이언트에서 보낸 메세지를 받을 prefix
        registry.enableSimpleBroker("/room");    //해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
    }
}