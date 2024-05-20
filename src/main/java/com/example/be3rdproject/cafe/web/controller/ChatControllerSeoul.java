package com.example.be3rdproject.cafe.web.controller;

import com.example.be3rdproject.cafe.repository.chatmessageseoul.ChatMessagesSeoul;
import com.example.be3rdproject.cafe.service.ChatMessageServiceSeoul;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/seoul-chat")
@RequiredArgsConstructor
@Slf4j
public class ChatControllerSeoul {

    private final ChatMessageServiceSeoul chatMessageServiceSeoul;

    @GetMapping("")
    @Operation(summary = "서울 채팅 전체 내용 조회")
    public Page<ChatMessagesSeoul> getMessages(@PageableDefault(size = 20) Pageable pageable) {
        return chatMessageServiceSeoul.getMessagesByPage(pageable);
    }
    @PostMapping("")
    @Operation(summary = "서울 채팅 전송")
    public void sendMessage(@RequestBody ChatMessagesSeoul chatMessage) {
        // senderId를 생성하여 ChatMessagesSeoul 객체에 설정
        String senderId = UUID.randomUUID().toString(); // 랜덤으로 생성된 UUID 사용
        chatMessage.setSenderId(senderId);

        chatMessageServiceSeoul.saveMessage(chatMessage);
    }
}
