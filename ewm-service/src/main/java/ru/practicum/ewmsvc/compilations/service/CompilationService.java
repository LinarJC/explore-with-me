package ru.practicum.ewmsvc.compilations.service;

import ru.practicum.ewmsvc.compilations.dto.CompilationDto;
import ru.practicum.ewmsvc.compilations.dto.NewCompilationDto;
import ru.practicum.ewmsvc.compilations.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> get(Boolean pinned, Integer from, Integer size);

    CompilationDto get(Long compId);

    CompilationDto save(NewCompilationDto compilationDto);

    void delete(Long compId);

    CompilationDto update(Long compId, UpdateCompilationRequest request);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void unpin(Long compId);

    void pin(Long compId);
}
