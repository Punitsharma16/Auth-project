package com.example.auth_app_backend.DiningService.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryDto {

    private String id;
    private String name;
    private String icon;
    private int itemCount;
}
