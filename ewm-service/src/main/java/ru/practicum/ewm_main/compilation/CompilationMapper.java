package ru.practicum.ewm_main.compilation;

import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.dto.ShortCompilationDto;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.event.EventMapper;

import java.util.stream.Collectors;

public class CompilationMapper {
    public static Compilation toCompilation(CompilationDto compilationDto) {
        return Compilation
                .builder()
                .id(compilationDto.getId())
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .events(compilationDto.getEvents().stream().map(EventMapper::toEvent).collect(Collectors.toList()))
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto
                .builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream().map(EventMapper::toShortEventDto).collect(Collectors.toList()))
                .build();
    }

    public static Compilation toCompilation(ShortCompilationDto compilationDto) {
        return Compilation
                .builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .build();
    }
}
