package com.example.auth_app_backend.botinfo.service;

import com.example.auth_app_backend.botinfo.dto.BotDto;
import com.example.auth_app_backend.botinfo.entity.BotEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface BotInfoService {

        BotEntity createBot(BotEntity botEntity);

        BotEntity updateBot(String botId , BotDto botEntity);

        List<BotEntity> getAllBotsByUserId(UUID id);

        ResponseEntity<String> saveKnowledge(String botId , String knowledgeType , String content , MultipartFile file);
}
