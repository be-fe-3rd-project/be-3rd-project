package com.example.be3rdproject.cafe.repository.chatmessageseoul;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "chat_messages_seoul")
public class ChatMessagesSeoul {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column (name ="content")
    private String content;
    @Column (name="senderId")
    private String senderId; // senderId 추가
    @Column (name = "timestamp")
    private LocalDateTime timestamp; // DATETIME에서 TIMESTAMP로 변경
}