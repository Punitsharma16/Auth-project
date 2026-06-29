package com.example.auth_app_backend.DiningService.MenuManagement.MenuItem;

import ch.qos.logback.core.model.ComponentModel;
import com.example.auth_app_backend.DiningService.dto.MenuItemDto;
import com.example.auth_app_backend.DiningService.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    @Mapping(source = "category.id",   target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    MenuItemDto toDto(MenuItem menuItem);

    @Mapping(target = "category", ignore = true)
    MenuItem toEntity(MenuItemDto dto);


}
