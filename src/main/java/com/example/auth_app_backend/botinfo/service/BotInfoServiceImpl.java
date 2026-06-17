package com.example.auth_app_backend.botinfo.service;

import com.example.auth_app_backend.botinfo.dto.BotDto;
import com.example.auth_app_backend.botinfo.entity.BotEntity;
import com.example.auth_app_backend.botinfo.repository.BotInfoRepo;
import com.example.auth_app_backend.chatbot.service.ChunkService;
import com.example.auth_app_backend.chatbot.service.KnowledgeService;
import com.example.auth_app_backend.chatbot.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class BotInfoServiceImpl implements BotInfoService {

    @Autowired
    BotInfoRepo botInfoRepo;

    @Autowired
    PdfService pdfService;

    @Autowired
    KnowledgeService knowledgeService;

    @Autowired
    ChunkService chunkService;

    @Override
    public BotEntity createBot(BotEntity botEntity) {
            return botInfoRepo.save(botEntity);
    }

    @Override
    public BotEntity updateBot(String botId, BotDto botInfo) {
        BotEntity bot = botInfoRepo.findByBotId(botId).orElseThrow(() -> new RuntimeException("Bot not found with id: " + botId));
        bot.setName(botInfo.getName());
        bot.setSystemPrompt(botInfo.getSystemPrompt());
        bot.setTone(botInfo.getTone());
        bot.setBotScript(botInfo.getBotScript());
        bot.setRole(botInfo.getRole());
        bot.setDescription(botInfo.getDescription());
        bot.setColor(botInfo.getColor());
        bot.setMaxResponseLength(botInfo.getMaxResponseLength());
        bot.setWelcomeMessage(botInfo.getWelcomeMessage());
        bot.setLanguage(botInfo.getLanguage());
        bot.setContentType(botInfo.getContentType());
        return botInfoRepo.save(bot);
    }

    @Override
    public List<BotEntity> getAllBotsByUserId(UUID id) {
        return botInfoRepo.findAllByUserId(id).orElseThrow(() -> new RuntimeException("No bots found for user with id: " + id));
    }

    @Override
    public ResponseEntity<String> saveKnowledge(String botId, String knowledgeType, String content, MultipartFile file) {
        String text = "";
        switch (knowledgeType.toLowerCase()){
            case "pdf":
                text = pdfService.extractText(file);
                break;
            case "website":
                text = pdfService.websiteText(content);
                break;
            case "content":
                text = content;
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid knowledge type. Must be 'pdf', 'website' or 'content'.");
        }

        List<String> chunks = chunkService.chunkText(text);
        knowledgeService.saveChunks(botId, chunks, "pdf");
        return ResponseEntity.ok("PDF knowledge saved successfully");
    }
}
