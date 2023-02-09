package ru.practicum.ewm_main.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
