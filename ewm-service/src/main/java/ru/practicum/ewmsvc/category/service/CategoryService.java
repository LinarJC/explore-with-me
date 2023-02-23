package ru.practicum.ewmsvc.category.service;

import ru.practicum.ewmsvc.category.dto.CategoryDto;
import ru.practicum.ewmsvc.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> get(Integer from, Integer size);

    CategoryDto get(Long id);

    CategoryDto update(Long catId, NewCategoryDto categoryDto);

    CategoryDto save(NewCategoryDto categoryDto);

    void delete(Long catId);
}
