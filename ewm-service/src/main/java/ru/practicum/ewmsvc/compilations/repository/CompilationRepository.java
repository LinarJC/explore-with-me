package ru.practicum.ewmsvc.compilations.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmsvc.compilations.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    Page<Compilation> getCompilationsByPinned(Boolean pinned, Pageable pageable);

    Compilation getCompilationByTitle(String title);
}
