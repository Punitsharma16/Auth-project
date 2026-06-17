package com.example.auth_app_backend.chatbot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {

    private String reply;
    private String sessionId;
    private String timestamp;

}
