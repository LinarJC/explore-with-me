package ru.practicum.ewmsvc.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmsvc.category.model.Category;

public interface CategoryReposirory extends JpaRepository<Category, Long> {

    Boolean existsCategoryByName(String name);
}
