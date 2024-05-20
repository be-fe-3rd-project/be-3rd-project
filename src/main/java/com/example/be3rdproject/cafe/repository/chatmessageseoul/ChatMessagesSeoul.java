package com.example.be3rdproject.cafe.repository.chatmessageseoul;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_messages_seoul")
public class ChatMessagesSeoul {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name ="message")
    private String message;
    @Column (name="senderId")
    private String senderId; // senderId 추가
    @Column (name = "timestamp")
    private LocalDateTime timestamp; // DATETIME에서 TIMESTAMP로 변경
}