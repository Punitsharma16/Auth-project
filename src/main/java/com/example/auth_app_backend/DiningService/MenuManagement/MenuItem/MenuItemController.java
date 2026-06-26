package com.example.auth_app_backend.DiningService.MenuManagement.MenuItem;

import com.example.auth_app_backend.DiningService.dto.MenuCategoryDto;
import com.example.auth_app_backend.DiningService.dto.MenuItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
    @Autowired
MenuItemService menuItemService;
    @PostMapping("/create")
    public ResponseEntity<MenuItemDto> create(@RequestBody MenuItemDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemService.createMenuItem(dto));
    }

    @GetMapping("/getmenus")
    public ResponseEntity<List<MenuItemDto>> getAll() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MenuItemDto>> getByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByCategory(String.valueOf(categoryId)));
    }
    @GetMapping("/available")
    public ResponseEntity<List<MenuItemDto>> getAvailable() {
        return ResponseEntity.ok(menuItemService.getAvailableMenuItems());
    }
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDto> update(@PathVariable UUID id, @RequestBody MenuItemDto dto) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<MenuItemDto> toggleAvailability(@PathVariable UUID id) {
        return ResponseEntity.ok(menuItemService.toggleAvailability(id));
    }
    @GetMapping("/public")
    public ResponseEntity<List<MenuCategoryDto>> getFullMenu() {
        return ResponseEntity.ok(menuItemService.getFullMenu());
    }



}
