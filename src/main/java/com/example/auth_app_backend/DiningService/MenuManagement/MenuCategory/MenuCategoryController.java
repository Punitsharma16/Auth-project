package com.example.auth_app_backend.DiningService.MenuManagement.MenuCategory;

import com.example.auth_app_backend.DiningService.dto.MenuCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class MenuCategoryController {
@Autowired
MenuCategoryService menuCategoryService;
//    MenuCategoryController
//
//    POST   /api/menu/categories
//    GET    /api/menu/categories
//    GET    /api/menu/categories/{id}
//    PUT    /api/menu/categories/{id}
//    DELETE /api/menu/categories/{id}


    @PostMapping
    public ResponseEntity<MenuCategoryDto> create(@RequestBody MenuCategoryDto dto) {
        return ResponseEntity.ok(menuCategoryService.create(dto));
    }


    @GetMapping
    public ResponseEntity<List<MenuCategoryDto>> getAll() {

        return ResponseEntity.ok(menuCategoryService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MenuCategoryDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(
                menuCategoryService.getById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<MenuCategoryDto> update(@PathVariable String id, @RequestBody MenuCategoryDto dto) {
        return ResponseEntity.ok(menuCategoryService.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        menuCategoryService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }


}
