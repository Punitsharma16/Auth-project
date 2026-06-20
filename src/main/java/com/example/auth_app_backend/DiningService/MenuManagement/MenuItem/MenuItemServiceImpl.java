package com.example.auth_app_backend.DiningService.MenuManagement.MenuItem;

import com.example.auth_app_backend.DiningService.MenuManagement.MenuCategory.MenuCategoryRepository;
import com.example.auth_app_backend.DiningService.dto.MenuItemDto;
import com.example.auth_app_backend.DiningService.entity.MenuCategory;
import com.example.auth_app_backend.DiningService.entity.MenuItem;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuItemServiceImpl implements MenuItemService {
    @Autowired
    MenuCategoryRepository menuCategoryRepository;
    @Autowired
    MenuItemRepository menuItemRepository;
    @Autowired
    MenuItemMapper menuItemMapper;

    @Override
    public MenuItemDto createMenuItem(MenuItemDto dto) {
        MenuCategory category = menuCategoryRepository.findById(String.valueOf(UUID.fromString(dto.getCategoryId())))
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getCategoryId()));

        MenuItem item = menuItemMapper.toEntity(dto);
        item.setCategory(category);
        return menuItemMapper.toDto(menuItemRepository.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemDto> getAllMenuItems() {
        return menuItemRepository.findAll().stream().map(menuItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public MenuItemDto getMenuItemById(UUID id) {
        return menuItemMapper.toDto(findOrThrow(id));
    }

    private MenuItem findOrThrow(UUID id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found: " + id));
    }


    @Override
    @Transactional(readOnly = true)
    public List<MenuItemDto> getMenuItemsByCategory(UUID categoryId) {
        return menuItemRepository.findByCategoryId(categoryId)
                .stream().map(menuItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemDto> getAvailableMenuItems() {
        return menuItemRepository.findByIsAvailableTrue().stream().map(menuItemMapper::toDto).toList();

    }

    @Override
    public MenuItemDto updateMenuItem(UUID id, MenuItemDto dto) {
        MenuItem existing = findOrThrow(id);
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setImage(dto.getImage());
        existing.setVeg(dto.isVeg());
        existing.setAvailable(dto.isAvailable());
        existing.setRating(dto.getRating());
        existing.setPreparationTime(dto.getPreparationTime());
        if (dto.getCategoryId() != null) {
            MenuCategory category = menuCategoryRepository.findById(String.valueOf(UUID.fromString(dto.getCategoryId()))).orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
            existing.setCategory(category);
        }
        return menuItemMapper.toDto(menuItemRepository.save(existing));

    }
    @Override
    public void deleteMenuItem(UUID id) {
        menuItemRepository.delete(findOrThrow(id));
    }

    @Override
    public MenuItemDto toggleAvailability(UUID id) {
        MenuItem item = findOrThrow(id);
        item.setAvailable(!item.isAvailable());
        return menuItemMapper.toDto(menuItemRepository.save(item));
    }


}
