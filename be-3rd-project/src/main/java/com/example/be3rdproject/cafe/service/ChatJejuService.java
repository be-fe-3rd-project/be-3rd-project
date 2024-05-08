package com.example.be3rdproject.cafe.service;


import com.example.be3rdproject.cafe.dto.ChatJejuMessageDto;
import com.example.be3rdproject.cafe.repository.chat.ChatJeju;
import com.example.be3rdproject.cafe.repository.chat.ChatJejuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ChatJejuService {

    private final ChatJejuRepository chatJejuRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public Page<ChatJeju> getMessagesByPage(Pageable pageable) {
        return chatJejuRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional
    public void sendMessage(ChatJejuMessageDto message){
        //채팅 메세지를 생성
        ChatJeju chatJeju = ChatJeju.builder()
                .sender(message.getSender())
                .message(message.getMessage())
                .build();

        //생성된 채팅 메세지를 저장
        chatJejuRepository.save(chatJeju);

        //메세지 전송을 모든 사용자에게
        messagingTemplate.convertAndSend("send/chat/message",message);


    }


}
