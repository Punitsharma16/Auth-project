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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long conversationId;

    private String role; // USER / BOT

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime timestamp;
}
