package com.example.auth_app_backend.chatbot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // internal ID

    @Column(unique = true, nullable = false)
    private String sessionId; // external ID (UUID)

    private String chatbotId;

    private String userId;
    private String visitorId;

    private LocalDateTime createdAt;
}
