package ru.practicum.ewmsvc.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmsvc.category.service.CategoryService;
import ru.practicum.ewmsvc.category.dto.CategoryDto;
import ru.practicum.ewmsvc.category.dto.NewCategoryDto;
import ru.practicum.ewmsvc.util.Create;
import ru.practicum.ewmsvc.util.Update;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PatchMapping("/{catId}")
    public CategoryDto update(@PathVariable Long catId,
                                     @Validated({Update.class}) @RequestBody NewCategoryDto categoryDto) {
        log.info("Request endpoint: 'PATCH /admin/categories'");
        return categoryService.update(catId, categoryDto);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryDto save(@Validated({Create.class}) @RequestBody NewCategoryDto categoryDto) {
        log.info("Request endpoint: 'POST /admin/categories'");
        return categoryService.save(categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long catId) {
        log.info("Request endpoint: 'DELETE /admin/categories/{}'", catId);
        categoryService.delete(catId);
    }
}
