package ru.practicum.ewm_main.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PatchMapping
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("update category with id {}", categoryDto.getId());
        return categoryService.updateCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.info("create category");
        return categoryService.createCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        log.info("delete category with id {}", id);
        categoryService.deleteCategory(id);
    }
}
