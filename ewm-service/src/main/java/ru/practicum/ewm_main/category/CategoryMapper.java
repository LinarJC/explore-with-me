package ru.practicum.ewm_main.category;

import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.model.Category;

public class CategoryMapper {
    public static Category toCategory(CategoryDto categoryDto) {
        return Category
                .builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toCategory(NewCategoryDto categoryDto) {
        return Category
                .builder()
                .name(categoryDto.getName())
                .build();
    }
}
