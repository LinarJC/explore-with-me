package ru.practicum.ewmsvc.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.category.dto.CategoryDto;
import ru.practicum.ewmsvc.category.dto.NewCategoryDto;
import ru.practicum.ewmsvc.category.mapper.CategoryMapper;
import ru.practicum.ewmsvc.category.model.Category;
import ru.practicum.ewmsvc.category.repository.CategoryRepository;
import ru.practicum.ewmsvc.event.repository.EventRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
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
    public List<CategoryDto> get(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return StreamSupport.stream(categories.spliterator(), false)
                .map(categoryMapper::mapCategoryToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto get(Long id) {
            Category category = categoryRepository.getReferenceById(id);
            return categoryMapper.mapCategoryToDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long catId, NewCategoryDto categoryDto) {
        if (categoryRepository.existsCategoryByName(categoryDto.getName())) {
            throw new ValidationException("Empty field");
        }
        Category category = categoryRepository.getReferenceById(catId);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return categoryMapper.mapCategoryToDto(category);
    }

    @Override
    @Transactional
    public CategoryDto save(@NotBlank NewCategoryDto categoryDto) {
        if (categoryRepository.existsCategoryByName(categoryDto.getName())) {
            throw new ValidationException("This name is exist");
        }
        Category category = categoryMapper.mapNewToCategory(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.mapCategoryToDto(category);
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        if (eventRepository.countEventsByCategory(catId) > 0) {
            throw new ValidationException("You can't delete a category with related events");
        }
        categoryRepository.deleteById(catId);
    }
}

