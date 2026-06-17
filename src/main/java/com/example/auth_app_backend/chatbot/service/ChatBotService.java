package com.example.auth_app_backend.chatbot.service;

import com.example.auth_app_backend.chatbot.entity.Conversation;
import com.example.auth_app_backend.chatbot.entity.Message;
import com.example.auth_app_backend.chatbot.dto.ChatRequest;
import com.example.auth_app_backend.chatbot.dto.ChatResponse;

import java.util.List;

public interface ChatBotService {
    ChatResponse chat(String botId, ChatRequest request , String userId);

    List<Conversation> getBotConversations(String botId, String userId);

    List<Message> getMessagesByConversationId(Long conversationId);
}
