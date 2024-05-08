package com.example.be3rdproject.cafe.web.controller;

import com.example.be3rdproject.cafe.dto.ChatJejuMessageDto;
import com.example.be3rdproject.cafe.repository.chat.ChatJeju;
import com.example.be3rdproject.cafe.service.ChatJejuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/jeju-chat")
@RequiredArgsConstructor
public class ChatJejuController {

    private final ChatJejuService chatJejuService;

    @GetMapping("")
    public Page<ChatJeju> getMessages(@PageableDefault(size = 20) Pageable pageable) {
        return chatJejuService.getMessagesByPage(pageable);
    }

    @PostMapping("")
    public void sendMessage(@RequestBody ChatJejuMessageDto message) {
        chatJejuService.sendMessage(message);
    }
}
