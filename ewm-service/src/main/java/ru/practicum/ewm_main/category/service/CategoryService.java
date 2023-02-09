package ru.practicum.ewm_main.category.service;

import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(Long id);

    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto createCategory(NewCategoryDto categoryDto);

    void deleteCategory(Long id);
}
