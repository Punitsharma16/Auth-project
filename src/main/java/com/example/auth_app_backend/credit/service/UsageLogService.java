package com.example.auth_app_backend.credit.service;

import com.example.auth_app_backend.credit.entity.UsageLog;
import com.example.auth_app_backend.credit.repository.UsageLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsageLogService {

    @Autowired
    UsageLogRepo usageLogRepo;

    public void logUsage(String userId, String botId, String input, String output, Long creditsUsed) {
        usageLogRepo.save(
                UsageLog.builder()
                        .botId(botId)
                        .userId(userId)
                        .messageLength(input.length())
                        .timestamp(LocalDateTime.now())
                        .responseLength(output.length())
                        .creditsUsed(creditsUsed)
                        .build()
        );
    }

}
