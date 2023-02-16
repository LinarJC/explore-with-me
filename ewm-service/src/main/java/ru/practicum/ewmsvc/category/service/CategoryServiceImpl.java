package ru.practicum.ewmsvc.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewmsvc.category.dto.CategoryDto;
import ru.practicum.ewmsvc.category.dto.NewCategoryDto;
import ru.practicum.ewmsvc.category.mapper.CategoryMapper;
import ru.practicum.ewmsvc.category.model.Category;
import ru.practicum.ewmsvc.category.repository.CategoryRepository;
import ru.practicum.ewmsvc.event.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final EventRepository eventRepository;

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return StreamSupport.stream(categories.spliterator(), false)
                .map(categoryMapper::mapCategoryToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such category");
        }
        Category category = categoryRepository.getReferenceById(id);
        return categoryMapper.mapCategoryToDto(category);
    }

    @Override
    public CategoryDto patchCategory(Long catId, NewCategoryDto categoryDto) {
        if (categoryRepository.existsCategoryByName(categoryDto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This name is exist");
        }
        Category category = categoryRepository.getReferenceById(catId);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return categoryMapper.mapCategoryToDto(category);
    }

    @Override
    public CategoryDto saveCategory(NewCategoryDto categoryDto) {
        if (categoryDto.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uncorrected request");
        }
        if (categoryRepository.existsCategoryByName(categoryDto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This name is exist");
        }
        Category category = categoryMapper.mapNewToCategory(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.mapCategoryToDto(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        if (eventRepository.findEventsByCategory(catId).size() > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You can't delete a category with related events");
        }
        categoryRepository.deleteById(catId);
    }
}

