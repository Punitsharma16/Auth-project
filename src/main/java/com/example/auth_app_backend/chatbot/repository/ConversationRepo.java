package com.example.auth_app_backend.chatbot.repository;

import com.example.auth_app_backend.chatbot.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepo extends JpaRepository<Conversation , Long> {
    Optional<Conversation> findBySessionId(String sessionId);
    List<Conversation> findByChatbotIdAndUserId(String botId , String userId);
}
