package com.example.auth_app_backend.DiningService.MenuManagement.MenuItem;

import com.example.auth_app_backend.DiningService.dto.MenuItemDto;

import java.util.List;
import java.util.UUID;

public interface MenuItemService {
    MenuItemDto createMenuItem(MenuItemDto dto);

    List<MenuItemDto> getAllMenuItems();

    MenuItemDto getMenuItemById(UUID id);

  List<MenuItemDto> getMenuItemsByCategory(UUID categoryId);

    List<MenuItemDto> getAvailableMenuItems();

    MenuItemDto updateMenuItem(UUID id, MenuItemDto dto);

    void deleteMenuItem(UUID id);

    MenuItemDto toggleAvailability(UUID id);
}
