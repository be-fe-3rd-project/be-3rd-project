package com.example.be3rdproject.cafe.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatJejuMessageDto {

    private String sender;
    private String message;
    private LocalDateTime createdAt;
}
