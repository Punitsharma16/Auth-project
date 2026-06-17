package com.example.auth_app_backend.chatbot.service;

import com.example.auth_app_backend.botinfo.entity.BotEntity;
import com.example.auth_app_backend.botinfo.repository.BotInfoRepo;
import com.example.auth_app_backend.chatbot.entity.Conversation;
import com.example.auth_app_backend.chatbot.repository.ConversationRepo;
import com.example.auth_app_backend.chatbot.entity.Message;
import com.example.auth_app_backend.chatbot.repository.MessageRepo;
import com.example.auth_app_backend.chatbot.dto.ChatRequest;
import com.example.auth_app_backend.chatbot.dto.ChatResponse;
import com.example.auth_app_backend.credit.service.CreditService;
import com.example.auth_app_backend.credit.service.UsageLogService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ChatBotImpl implements ChatBotService {

    ChatClient chatClient;

    public ChatBotImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @Autowired
    BotInfoRepo botInfoRepo;

    @Autowired
    ConversationRepo conversationRepo;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    KnowledgeService knowledgeService;

    @Autowired
    CreditService creditService;

    @Autowired
    UsageLogService usageLogService;

    @Override
    public ChatResponse chat(String botId, ChatRequest request , String userId) {

        BotEntity botEntity = botInfoRepo.findByBotId(botId).orElseThrow(()-> new RuntimeException("Bot Not Found"));
        Conversation conversation = null;

        if(request.getSessionId() != null ){
            conversation = conversationRepo.findBySessionId(request.getSessionId()).orElseThrow(()-> new RuntimeException("Invalid Session"));
            if (!conversation.getUserId().equals(userId)){
                throw new RuntimeException("Unauthorized Session");
            }
        } else {
            conversation =conversationRepo.save(Conversation
                    .builder()
                    .sessionId(UUID.randomUUID().toString())
                    .userId(userId)
                    .visitorId(request.getVisitorId())
                    .chatbotId(botId)
                    .createdAt(LocalDateTime.now())
                    .build()
            );
        }

        List<Message> history = messageRepo.findTop10ByConversationIdOrderByTimestampDesc(conversation.getId());

        List<String> docs =
                knowledgeService.search(
                        botId,
                        request.getMessage()
                );

        String context = String.join("\n", docs);
        if (context.trim().isEmpty()) {
            context = "NO_CONTEXT";
        }

        String prompt = PromptBuilder.build(botEntity , request.getMessage() , history , context);

        System.out.println("The bot find from id is -> " + botEntity);
        creditService.checkEstimateCredit(userId , request.getMessage());
        String reply = "";

        messageRepo.save(
                Message.builder()
                        .content(request.getMessage())
                        .conversationId(conversation.getId())
                        .role("USER")
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        try {
            System.out.println("The prompt send to AI service is -> " + prompt);
            reply = chatClient
                    .prompt(prompt)
                    .call()
                    .content();
            System.out.println("The Resply from the AI service is -> " + reply);
        } catch (Exception e) {
            throw new RuntimeException("AI service failed");
        }

        creditService.deductCredits(userId, request.getMessage() , reply);
        usageLogService.logUsage(userId , botId , request.getMessage() , reply , creditService.calculateCredits(request.getMessage() , reply));
        messageRepo.save(
                Message.builder()
                        .conversationId(conversation.getId())
                        .role("BOT")
                        .content(reply)
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        return ChatResponse.builder()
                .reply(reply)
                .sessionId(conversation.getSessionId())
                .build();    }


    public List<Conversation> getBotConversations(String botId , String userId){
        return conversationRepo.findByChatbotIdAndUserId(botId , userId);
    }

    @Override
    public List<Message> getMessagesByConversationId(Long conversationId) {
        return messageRepo.findAllByConversationIdOrderByTimestampDesc(conversationId);
    }
}
