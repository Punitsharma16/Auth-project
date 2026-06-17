package com.example.auth_app_backend.chatbot.service;

import com.example.auth_app_backend.botinfo.entity.BotEntity;
import com.example.auth_app_backend.chatbot.entity.Message;

import java.util.List;

public class PromptBuilder {

    private static final List<String> DEFAULT_RULES = List.of(
         "Answer clearly and concisely",
                 "Do not hallucinate",
        "If unsure, say 'I don't know'",
        "Use the provided context to answer",
            "Do not use external knowledge"
    );


    public static String build(BotEntity bot, String userMessage , List<Message> history , String context) {

        StringBuilder rulesBuilder = new StringBuilder();
        StringBuilder historyText = new StringBuilder();

        // 1️⃣ Default Rules
        DEFAULT_RULES.forEach(rule -> rulesBuilder.append("- ").append(rule).append("\n"));

        // 2️⃣ DB Rules
        if (bot.getRules() != null) {
            String[] dbRules = bot.getRules().split("\n");
            for (String rule : dbRules) {
                rulesBuilder.append("- ").append(rule).append("\n");
            }
        }

        if(!history.isEmpty()){
            history.stream()
                    .sorted((a,b)-> a.getTimestamp().compareTo(b.getTimestamp()))
                    .forEach(msg -> historyText.append(msg.getContent()).append("\n"));
        }

        return """
        You are a professional AI assistant.

        ROLE:
        %s

        TONE:
        %s
        
        CONTEXT:
        %s
        
        CONVERSATION HISTORY:
        %s

        RULES:
        %s

        USER QUESTION:
        %s

        RESPONSE:
        """.formatted(
                bot.getSystemPrompt(),
                bot.getTone(),
                context,
                historyText.toString(),
                rulesBuilder.toString(),
                userMessage
        );
    }
}
