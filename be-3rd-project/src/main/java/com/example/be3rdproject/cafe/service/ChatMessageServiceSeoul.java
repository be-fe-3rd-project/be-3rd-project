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

    // 클라이언트에서는 페이지 번호와 페이지 크기를 파라미터로 서버에 요청을 보내야 합니다.
    // /api/chat/messages?page=0&size=10와 같은 식으로 요청하면, 서버는 첫 번째 페이지의 10개의 채팅 메시지를 반환할

}
