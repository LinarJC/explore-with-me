package ru.practicum.ewmsvc.category.service;

import ru.practicum.ewmsvc.category.dto.CategoryDto;
import ru.practicum.ewmsvc.category.dto.NewCategoryDto;

import javax.validation.ValidationException;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Long id) throws ValidationException;

    CategoryDto patchCategory(Long catId, NewCategoryDto categoryDto);

    CategoryDto saveCategory(NewCategoryDto categoryDto) throws ValidationException;

    void deleteCategory(Long catId);
}
