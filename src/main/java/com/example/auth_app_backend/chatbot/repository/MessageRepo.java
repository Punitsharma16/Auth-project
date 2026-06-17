package com.example.auth_app_backend.chatbot.repository;

import com.example.auth_app_backend.chatbot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message , Long>{
    List<Message> findTop10ByConversationIdOrderByTimestampDesc(Long conversationId);
    List<Message> findAllByConversationIdOrderByTimestampDesc(Long conversationId);
}
