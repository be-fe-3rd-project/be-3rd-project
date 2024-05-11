package com.example.be3rdproject.cafe.repository.chatmessageseoul;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessagesSeoulRepository extends JpaRepository<ChatMessagesSeoul,Long> {
    Page<ChatMessagesSeoul> findAllByOrderByTimestampDesc(Pageable pageable);

}