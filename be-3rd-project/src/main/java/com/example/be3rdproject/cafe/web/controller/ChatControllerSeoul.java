package com.example.be3rdproject.cafe.web.controller;

import com.example.be3rdproject.cafe.repository.chatmessageseoul.ChatMessagesSeoul;
import com.example.be3rdproject.cafe.service.ChatMessageServiceSeoul;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
        chatMessageServiceSeoul.saveMessage(chatMessage);
    }
}
