package com.example.auth_app_backend.botinfo.repository;

import com.example.auth_app_backend.botinfo.entity.BotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BotInfoRepo extends JpaRepository<BotEntity, Integer> {
    Optional<BotEntity> findByBotId(String botId);
    Optional<List<BotEntity>> findAllByUserId(UUID userId);

}
