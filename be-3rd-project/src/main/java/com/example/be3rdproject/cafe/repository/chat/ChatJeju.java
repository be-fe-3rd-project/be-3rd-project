package com.example.be3rdproject.cafe.repository.chat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_Jeju")
public class ChatJeju {
    @Id
    @Column(name = "chatJeju_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "message")
    private String message;

    @Column(name = "sender")
    private String sender;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


    public ChatJeju(String message,String sender, LocalDateTime createdAt){
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;


    }

}
