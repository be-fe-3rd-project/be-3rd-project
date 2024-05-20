package com.example.be3rdproject.cafe.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageSeoulDto {

    private String senderId;
    private String message;
    private LocalDateTime timestamp;
}
