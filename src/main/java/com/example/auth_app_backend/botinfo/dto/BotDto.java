package com.example.auth_app_backend.botinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BotDto {
    private String name;
    private String botId;
    private String systemPrompt;
    private String websiteUrl;
    private String tone;
    private String description;
    private String contentType;
    private String welcomeMessage;
    private String role;
    private String color;
    private long maxResponseLength;
    private String position;
    private String botScript;
    private String language;
    private String rules;
}
