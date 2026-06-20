package com.example.auth_app_backend.DiningService.MenuManagement.MenuCategory;

import com.example.auth_app_backend.DiningService.dto.MenuCategoryDto;
import com.example.auth_app_backend.DiningService.entity.MenuCategory;

public class MenuCategoryMapper {
    MenuCategoryDto toDto(MenuCategory entity){
        return MenuCategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .itemCount(entity.getItemCount())
                .build();
    }

    public MenuCategory toEntity(
            MenuCategoryDto dto) {

        return MenuCategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .icon(dto.getIcon())
                .itemCount(dto.getItemCount())
                .build();
    }

}
