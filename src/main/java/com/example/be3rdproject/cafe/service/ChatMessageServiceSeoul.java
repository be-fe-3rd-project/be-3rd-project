package com.example.be3rdproject.cafe.service;

import com.example.be3rdproject.cafe.repository.chatmessageseoul.ChatMessagesSeoul;
import com.example.be3rdproject.cafe.repository.chatmessageseoul.ChatMessagesSeoulRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceSeoul {
    private final ChatMessagesSeoulRepository chatMessagesSeoulRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public Page<ChatMessagesSeoul> getMessagesByPage(Pageable pageable) {
        return chatMessagesSeoulRepository.findAllByOrderByTimestampDesc(pageable);
    }
    @Transactional
    public void saveMessage(ChatMessagesSeoul messageDto) {
        ChatMessagesSeoul message = ChatMessagesSeoul.builder()
                .senderId(messageDto.getSenderId())
                .message(messageDto.getMessage())
                .build();
        chatMessagesSeoulRepository.save(message);

        messagingTemplate.convertAndSend("send/chat/message", messageDto);
    }



}