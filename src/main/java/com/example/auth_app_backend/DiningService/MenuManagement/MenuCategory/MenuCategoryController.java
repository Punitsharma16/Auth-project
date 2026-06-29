package com.example.auth_app_backend.DiningService.MenuManagement.MenuCategory;

import com.example.auth_app_backend.DiningService.dto.MenuCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/menu/category")
public class MenuCategoryController {
@Autowired
MenuCategoryService menuCategoryService;
 @PostMapping("/create")
    public ResponseEntity<MenuCategoryDto> create(@RequestBody MenuCategoryDto dto) {
        return ResponseEntity.ok(menuCategoryService.create(dto));
    }


    @GetMapping("/getallCategory")
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
