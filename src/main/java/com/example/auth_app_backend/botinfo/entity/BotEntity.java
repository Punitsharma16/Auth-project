package com.example.auth_app_backend.botinfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bot_info")
public class BotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private UUID userId;
    @Column(unique = true)
    private String botId;
    private String name;
    private String role;
    private String welcomeMessage;
    private String botScript;
    private String contentType;
    private String language;
    private String websiteUrl;
    private String description;
    private String color;
    private long maxResponseLength;
    private String position;
    private String systemPrompt;
    private String tone;
    @Column(columnDefinition = "TEXT")
    private String rules;


    @PrePersist
    public void generateUserCode() {
        this.botId = "BOT_" + UUID.randomUUID().toString().substring(0, 8);
    }


    @Override
    public String toString() {
        return "BotDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", botId='" + botId + '\'' +
                ", name='" + name + '\'' +
                ", tone='" + tone + '\'' +
                ", role='" + role + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", position='" + position + '\'' +
                ", contentType='" + contentType + '\'' +
                ", maxResponseLength='" + maxResponseLength + '\'' +
                ", welcomeMessage='" + welcomeMessage + '\'' +
                ", botScript='" + botScript + '\'' +
                ", language='" + language + '\'' +
                ", Rules='" + rules + '\'' +
                ", systemPrompt='" + systemPrompt + '\'' +
                '}';
    }

}
