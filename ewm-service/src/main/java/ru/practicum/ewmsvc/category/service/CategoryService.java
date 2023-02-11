package ru.practicum.ewmsvc.category.service;

import ru.practicum.ewmsvc.category.dto.CategoryDto;
import ru.practicum.ewmsvc.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Long id);

    CategoryDto patchCategory(Long catId, NewCategoryDto categoryDto);

    CategoryDto saveCategory(NewCategoryDto categoryDto);

    void deleteCategory(Long catId);
}
