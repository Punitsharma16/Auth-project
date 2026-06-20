package com.example.auth_app_backend.DiningService.MenuManagement.MenuCategory;

import com.example.auth_app_backend.DiningService.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository  extends JpaRepository<MenuCategory,String> {
}
