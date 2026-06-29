package com.example.auth_app_backend.DiningService.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuCategoryDto {

    private String id;
    private String name;
    private String icon;
    private int itemCount;
    private List<MenuItemDto> items;
}
