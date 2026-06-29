package com.example.auth_app_backend.DiningService.dto;

import com.example.auth_app_backend.DiningService.entity.ResourceType;
import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DiningResourceDto {

    private long id;
    private ResourceType resourceType;
    private String number;
    private String displayId;
    private boolean isOccupied;
    private Integer capacity;
    private String roomType;
    private Integer floor;
    private String qrUrl;
}
