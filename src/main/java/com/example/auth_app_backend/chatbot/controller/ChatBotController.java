package com.example.auth_app_backend.chatbot.controller;


import com.example.auth_app_backend.chatbot.entity.Conversation;
import com.example.auth_app_backend.chatbot.entity.Message;
import com.example.auth_app_backend.chatbot.dto.ChatRequest;
import com.example.auth_app_backend.chatbot.dto.ChatResponse;
import com.example.auth_app_backend.chatbot.service.ChatBotService;
import com.example.auth_app_backend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatBotController {

    @Autowired
    ChatBotService chatBotService;

    @PostMapping("/{botId}")
    public ChatResponse chat(@PathVariable String botId , @RequestBody ChatRequest request , @AuthenticationPrincipal User user){
        System.out.println("the bot id and the query is -> " + botId + "---" + request);
        return chatBotService.chat(botId , request , String.valueOf(user.getId()));
    }

    @GetMapping("/{botId}")
    public List<Conversation> getConversationHistory(@PathVariable String botId , @AuthenticationPrincipal User user) {
        System.out.println("the bot id is -> " + botId);
        return chatBotService.getBotConversations(botId, String.valueOf(user.getId()));
    }

    @GetMapping("/message/{conversationId}")
    public List<Message> getConversationById(@PathVariable Long conversationId , @AuthenticationPrincipal User user) {
        System.out.println("the conversation id is -> " + conversationId);
        return chatBotService.getMessagesByConversationId(conversationId);
    }

}
