package ru.practicum.ewmsvc.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmsvc.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsCategoryByName(String name);
}
