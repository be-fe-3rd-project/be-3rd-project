package com.example.be3rdproject.cafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns("*");
                // CORS 설정
                // 모든 경로("/**")에 대해 모든 출처("*")로부터의 요청을 허용 추후 프론트 연결시 localhost3030? 으로 변경 예정
            }
        };
    }
}