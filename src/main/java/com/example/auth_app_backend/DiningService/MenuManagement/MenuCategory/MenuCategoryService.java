package com.example.auth_app_backend.DiningService.MenuManagement.MenuCategory;

import com.example.auth_app_backend.DiningService.dto.MenuCategoryDto;

import java.util.List;

public interface MenuCategoryService {
    MenuCategoryDto create(MenuCategoryDto dto);

    List<MenuCategoryDto> getAll();

    MenuCategoryDto getById(String id);

    MenuCategoryDto update(String id, MenuCategoryDto dto);

    void delete(String id);
}
