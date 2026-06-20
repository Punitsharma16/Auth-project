package com.example.auth_app_backend.DiningService.MenuManagement.MenuItem;

import com.example.auth_app_backend.DiningService.dto.MenuItemDto;
import com.example.auth_app_backend.DiningService.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    List<MenuItem> findByCategoryId(UUID categoryId);

    List<MenuItem> findByIsAvailableTrue();
}
