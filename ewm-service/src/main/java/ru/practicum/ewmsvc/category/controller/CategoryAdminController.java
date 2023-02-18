package ru.practicum.ewmsvc.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewmsvc.category.repository.CategoryRepository;
import ru.practicum.ewmsvc.category.service.CategoryService;
import ru.practicum.ewmsvc.category.dto.CategoryDto;
import ru.practicum.ewmsvc.category.dto.NewCategoryDto;
import ru.practicum.ewmsvc.event.repository.EventRepository;
import ru.practicum.ewmsvc.util.Create;
import ru.practicum.ewmsvc.util.Update;

import javax.validation.ValidationException;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryService categoryService;

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@PathVariable Long catId,
                                     @Validated({Update.class}) @RequestBody NewCategoryDto categoryDto) {
        log.info("Request endpoint: 'PATCH /admin/categories'");
        if (categoryRepository.existsCategoryByName(categoryDto.getName())) {
            throw new ValidationException("Empty field",
                    new ResponseStatusException(HttpStatus.CONFLICT, "This name is exist"));
        }
        return categoryService.patchCategory(catId, categoryDto);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryDto saveCategory(
            @Validated({Create.class}) @RequestBody NewCategoryDto categoryDto) {
        log.info("Request endpoint: 'POST /admin/categories'");
        if (categoryDto.getName() == null) {
            throw new ValidationException("Empty field",
                    new ResponseStatusException(HttpStatus.CONFLICT, "Uncorrected request"));
        }
        if (categoryRepository.existsCategoryByName(categoryDto.getName())) {
            throw new ValidationException("This name is exist",
                    new ResponseStatusException(HttpStatus.CONFLICT, "This name is exist"));
        }
        return categoryService.saveCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        log.info("Request endpoint: 'DELETE /admin/categories/{}'", catId);
        if (eventRepository.getCountFindEventsByCategory(catId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You can't delete a category with related events");
        }
        categoryService.deleteCategory(catId);
    }
}
