package com.example.auth_app_backend.DiningService.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItemDto {

    private Long id;
    private String menuItemId;
    private String name;
    private int quantity;
    private double price;
    private String specialInstructions;
}
