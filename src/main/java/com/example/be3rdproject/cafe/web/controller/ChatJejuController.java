package com.example.be3rdproject.cafe.web.controller;

import com.example.be3rdproject.cafe.dto.ChatJejuMessageDto;
import com.example.be3rdproject.cafe.repository.chat.ChatJeju;
import com.example.be3rdproject.cafe.service.ChatJejuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jeju-chat")
@RequiredArgsConstructor
@Slf4j
public class ChatJejuController {

    private final ChatJejuService chatJejuService;

    @GetMapping("")
    @Operation(summary = "제주 채팅 전체 내용 조회")
    public Page<ChatJeju> getMessages(@PageableDefault(size = 20) Pageable pageable) {
        return chatJejuService.getMessagesByPage(pageable);
    }

    @PostMapping("")
    @Operation(summary = "제주 채팅 전송")
    public void sendMessage(@RequestBody ChatJejuMessageDto message) {
        chatJejuService.sendMessage(message);
    }
}
