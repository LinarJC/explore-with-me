package ru.practicum.ewm_main.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.service.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
public class PublicControllerCompilation {
    private final CompilationService compilationService;

    public PublicControllerCompilation(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam (required = false) Boolean pinned,
                                                @RequestParam (defaultValue = "0") int from,
                                                @RequestParam (defaultValue = "10") int size) {
        log.info("get compilations with param: pinned = {}, from = {}, size = {}", pinned, from, size);
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{id}")
    public CompilationDto getCompilation(@PathVariable Long id) {
        log.info("get compilation with id {}", id);
        return compilationService.getCompilation(id);
    }
}
