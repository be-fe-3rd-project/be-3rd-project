package com.example.be3rdproject.cafe.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatJejuRepository extends JpaRepository<ChatJeju, Integer> {
    Page<ChatJeju> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
