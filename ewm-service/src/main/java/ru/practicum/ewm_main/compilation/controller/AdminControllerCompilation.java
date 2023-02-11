package ru.practicum.ewm_main.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.dto.ShortCompilationDto;
import ru.practicum.ewm_main.compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
public class AdminControllerCompilation {
    private final CompilationService compilationService;

    public AdminControllerCompilation(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping
    public CompilationDto createCompilation(@Valid @RequestBody ShortCompilationDto compilationDto) {
        log.info("create new compilation");
        return compilationService.createCompilation(compilationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCompilation(@PathVariable Long id) {
        log.info("delete compilation with id {}", id);
        compilationService.deleteCompilation(id);
    }

    @DeleteMapping("/{id}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long id,
                                           @PathVariable Long eventId) {
        log.info("delete event with id {} from compilation with id {}", eventId, id);
        compilationService.deleteEventFromCompilation(id, eventId);
    }

    @PatchMapping("/{id}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long id,
                                      @PathVariable Long eventId) {
        log.info("add event with id {} to compilation with id {}", eventId, id);
        compilationService.addEventToCompilation(id, eventId);
    }

    @DeleteMapping("/{id}/pin")
    public void deleteCompilationFromMainPage(@PathVariable Long id) {
        log.info("delete compilation with id {} from main page", id);
        compilationService.deleteCompilationFromMainPage(id);
    }

    @PatchMapping("/{id}/pin")
    public void addCompilationToMainPage(@PathVariable Long id) {
        log.info("add compilation with id {} to main page", id);
        compilationService.addCompilationToMainPage(id);
    }
}
