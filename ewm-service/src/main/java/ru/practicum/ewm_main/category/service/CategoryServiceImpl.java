package ru.practicum.ewm_main.category.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.category.CategoryMapper;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.model.Category;
import ru.practicum.ewm_main.category.repository.CategoryRepository;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.exception.BadRequestException;
import ru.practicum.ewm_main.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, EventRepository eventRepository) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size))
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategory(Long id) {
        return CategoryMapper.toCategoryDto(getAndCheckCategory(id));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = getAndCheckCategory(categoryDto.getId());
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(categoryDto)));
    }

    @Override
    public void deleteCategory(Long id) {
        if (!eventRepository.findAllByCategoryId(id).isEmpty()) {
            throw new BadRequestException("only category without event can be delete");
        }
        categoryRepository.delete(getAndCheckCategory(id));
    }

    private Category getAndCheckCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id = " + id + " not found"));
    }
}
