package com.example.auth_app_backend.chatbot.dto;

import lombok.Data;

@Data
public class ChatRequest {

    private String message;
    private String sessionId;
    private String visitorId;
}
