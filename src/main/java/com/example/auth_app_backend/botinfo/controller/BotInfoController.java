package com.example.auth_app_backend.botinfo.controller;


import com.example.auth_app_backend.botinfo.dto.BotDto;
import com.example.auth_app_backend.botinfo.entity.BotEntity;
import com.example.auth_app_backend.botinfo.service.BotInfoService;
import com.example.auth_app_backend.chatbot.service.ChunkService;
import com.example.auth_app_backend.chatbot.service.KnowledgeService;
import com.example.auth_app_backend.chatbot.service.PdfService;
import com.example.auth_app_backend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/bot")
public class BotInfoController {

    @Autowired
    BotInfoService botInfoService;

    @Autowired
    PdfService pdfService;

    @Autowired
    ChunkService chunkService;

    @Autowired
    KnowledgeService knowledgeService;

    @PostMapping("/create")
    public ResponseEntity<BotEntity> createBot(@RequestBody BotDto botInfo, @AuthenticationPrincipal User user) {
        System.out.println("Email from authentication: " + user.getId());
        BotEntity entity = new BotEntity();
        entity.setName(botInfo.getName());
        entity.setSystemPrompt(botInfo.getSystemPrompt());
        entity.setUserId(user.getId());
//        entity.setWebsiteUrl(botInfo.getWebsiteUrl());
        entity.setTone(botInfo.getTone());
        entity.setBotScript(botInfo.getBotScript());
        entity.setRole(botInfo.getRole());
        entity.setDescription(botInfo.getDescription());
        entity.setColor(botInfo.getColor());
        entity.setMaxResponseLength(botInfo.getMaxResponseLength());
        entity.setWelcomeMessage(botInfo.getWelcomeMessage());
        entity.setLanguage(botInfo.getLanguage());
        entity.setContentType(botInfo.getContentType());
        BotEntity botDto = botInfoService.createBot(entity);
        return ResponseEntity.ok(botDto);
    }

    @PostMapping("/update/{botId}")
    public ResponseEntity<BotEntity> updateBot(@PathVariable String botId , @RequestBody BotDto botInfo, @AuthenticationPrincipal User user) {
        System.out.println("Email from authentication: " + user.getId());

        BotEntity botDto = botInfoService.updateBot(botId  , botInfo);
        return ResponseEntity.ok(botDto);
    }

    @GetMapping("/getAllBots")
    public ResponseEntity<List<BotEntity>> getAllBots(@AuthenticationPrincipal User user) {
        System.out.println("Email from authentication: " + user.getId());
        List<BotEntity> botEntities = botInfoService.getAllBotsByUserId(user.getId());
        return ResponseEntity.ok(botEntities);
    }

    @PostMapping("/pdf/{botId}")
    public ResponseEntity<String> uploadPdf(@PathVariable String botId , @RequestBody MultipartFile file){
        String pdfText = pdfService.extractText(file);
        List<String> pdfTextChunk = chunkService.chunkText(pdfText);
        knowledgeService.saveChunks(botId, pdfTextChunk , file.getName());
        return ResponseEntity.ok("Pdf Uploaded Successfully");
    }

    @PostMapping("/knowledge/{botId}")
    public ResponseEntity<String> uploadKnowledge(@PathVariable String botId , @RequestBody BotDto dto , @RequestParam(required = false) MultipartFile file) {
        return botInfoService.saveKnowledge(botId, dto.getContentType() , dto.getWebsiteUrl() , file);
    }


}
