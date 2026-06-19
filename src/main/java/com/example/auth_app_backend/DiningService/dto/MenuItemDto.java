package com.example.auth_app_backend.DiningService.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemDto {

    private Long id;
    private String itemCode;
    private String name;
    private String description;
    private double price;
    private String image;
    private String categoryId;
    private String categoryName;
    private boolean isVeg;
    private boolean isAvailable;
    private double rating;
    private int preparationTime;
}
