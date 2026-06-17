package com.example.auth_app_backend.common.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatBotClient {

    @Bean
    public ChatClient getChatClient (ChatClient.Builder builder , ChatMemory chatMemory){

        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder
                .defaultAdvisors(messageChatMemoryAdvisor)
                .defaultSystem("You are a assistant of Punit Sharma")
                .build();
    }
}
