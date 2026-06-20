package com.example.auth_app_backend.DiningService.MenuManagement.MenuCategory;

import com.example.auth_app_backend.DiningService.dto.MenuCategoryDto;
import com.example.auth_app_backend.DiningService.entity.MenuCategory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuCategoryServiceImpl implements MenuCategoryService {
    @Autowired
    MenuCategoryRepository menuCategoryRepository;
    @Autowired
    MenuCategoryMapper mapper;
    @Override
    public MenuCategoryDto create(MenuCategoryDto dto) {
         if(menuCategoryRepository.existsById(dto.getId())){
             throw new RuntimeException("Category already exist");
         }
        MenuCategory menuCategory= mapper.toEntity(dto);
         return mapper.toDto(menuCategoryRepository.save(menuCategory));
    }
    @Override
    @Transactional
    public List<MenuCategoryDto> getAll(){
       return menuCategoryRepository.findAll().stream().map(mapper::toDto).toList();
    }
    @Override
    @Transactional
    public MenuCategoryDto getById(String id){
        MenuCategory menuCategory= menuCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"));

        return mapper.toDto(menuCategory);
    }
    @Override
    public MenuCategoryDto update(String id, MenuCategoryDto dto) {

        MenuCategory category = menuCategoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.getName());
        category.setIcon(dto.getIcon());
        return mapper.toDto(menuCategoryRepository.save(category));
    }
    @Override
    public void delete(String id) {
        MenuCategory category = menuCategoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found"));
        menuCategoryRepository.delete(category);
    }

}
