package com.example.be3rdproject.cafe.web.controller;

import com.example.be3rdproject.cafe.repository.chatmessageseoul.ChatMessagesSeoul;
import com.example.be3rdproject.cafe.service.ChatMessageServiceSeoul;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatControllerSeoul {

    private final ChatMessageServiceSeoul chatMessageServiceSeoul;

    @GetMapping("/messages")
    public Page<ChatMessagesSeoul> getMessages(@PageableDefault(size = 20) Pageable pageable) {
        return chatMessageServiceSeoul.getMessagesByPage(pageable);
    }
    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody ChatMessagesSeoul chatMessage) {
        // senderId를 생성하여 ChatMessagesSeoul 객체에 설정
        String senderId = UUID.randomUUID().toString(); // 랜덤으로 생성된 UUID 사용
        chatMessage.setSenderId(senderId);

        chatMessageServiceSeoul.saveMessage(chatMessage);
    }
}
