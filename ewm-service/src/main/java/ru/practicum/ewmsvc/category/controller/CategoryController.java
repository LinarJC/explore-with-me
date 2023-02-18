package ru.practicum.ewmsvc.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewmsvc.category.repository.CategoryRepository;
import ru.practicum.ewmsvc.category.service.CategoryService;
import ru.practicum.ewmsvc.category.dto.CategoryDto;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        log.info("Request endpoint: 'GET /categories'");
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        log.info("Request endpoint: 'GET /categories/{}", catId);
        if (!categoryRepository.existsById(catId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such category");
        }
        return categoryService.getCategory(catId);
    }
}
