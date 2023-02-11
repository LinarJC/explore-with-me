package ru.practicum.ewm_main.compilationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.compilation.repository.CompilationRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class CompilationRepositoryTest {
    @Autowired
    private CompilationRepository compilationRepository;

    @Test
    void findAllByPinned() {
        compilationRepository.save(Compilation.builder().pinned(true).title("title").build());
        Page<Compilation> compilations = compilationRepository.findAllByPinned(true, Pageable.ofSize(10));
        assertThat(compilations.stream().count(), equalTo(1L));
    }
}
