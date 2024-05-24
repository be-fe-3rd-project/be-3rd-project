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
@Slf4j
@RequiredArgsConstructor
public class ChatMessageServiceSeoul {
    private final ChatMessagesSeoulRepository chatMessagesSeoulRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public Page<ChatMessagesSeoul> getMessagesByPage(Pageable pageable) {
        try {
            return chatMessagesSeoulRepository.findAllByOrderByTimestampDesc(pageable);
        } catch (Exception e) {
            log.error("페이지별로 메시지를 검색하는 중 오류 발생", e);
            throw new RuntimeException("메시지를 검색하지 못했습니다", e);
        }
    }

    @Transactional
    public void saveMessage(ChatMessagesSeoul messageDto) {
        try {
            ChatMessagesSeoul message = ChatMessagesSeoul.builder()
                    .senderId(messageDto.getSenderId())
                    .message(messageDto.getMessage())
                    .build();
            chatMessagesSeoulRepository.save(message);

            messagingTemplate.convertAndSend("send/chat/message", messageDto);
        } catch (Exception e) {
            log.error("메시지 저장 중 오류 발생", e);
            throw new RuntimeException("메시지를 저장하지 못했습니다", e);
        }
    }


}