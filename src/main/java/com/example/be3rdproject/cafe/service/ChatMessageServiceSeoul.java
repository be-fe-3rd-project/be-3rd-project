package com.example.be3rdproject.cafe.service;

import com.example.be3rdproject.cafe.repository.chatmessageseoul.ChatMessagesSeoul;
import com.example.be3rdproject.cafe.repository.chatmessageseoul.ChatMessagesSeoulRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageServiceSeoul {

    private final ChatMessagesSeoulRepository chatMessagesSeoulRepository;

    public Page<ChatMessagesSeoul> getMessagesByPage(Pageable pageable) {
        return chatMessagesSeoulRepository.findAllByOrderByTimestampDesc(pageable);
    }

    public void saveMessage(ChatMessagesSeoul message) {
        chatMessagesSeoulRepository.save(message);
    }



}