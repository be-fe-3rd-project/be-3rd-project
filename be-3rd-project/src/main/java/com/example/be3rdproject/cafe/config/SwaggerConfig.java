package com.example.be3rdproject.cafe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("3차 프로젝트")
                .description("Spring Boot를 이용한 웹 어플리케이션 API 입니다.")
                .contact(new Contact().name("be-3rd-project"));
        return new OpenAPI()
                .info(info);
    }
}
