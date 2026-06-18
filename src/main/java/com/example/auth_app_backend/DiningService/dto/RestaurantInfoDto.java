package com.example.auth_app_backend.DiningService.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RestaurantInfoDto {

    private String id;
    private String name;
    private String description;
    private String logo;
    private String coverImage;
    private double rating;
    private String address;
    private String openTime;
    private String closeTime;
    private boolean isOpen;
    private String upiQrImage;
}
