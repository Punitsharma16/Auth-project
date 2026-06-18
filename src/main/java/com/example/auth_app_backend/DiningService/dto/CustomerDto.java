package com.example.auth_app_backend.DiningService.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerDto {

    private UUID id;
    private String name;
    private String mobile;
    private String email;
    private int totalOrders;
    private int loyaltyPoints;
    private Instant createdAt;
}
