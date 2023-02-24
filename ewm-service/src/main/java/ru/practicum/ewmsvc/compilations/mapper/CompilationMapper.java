package ru.practicum.ewmsvc.compilations.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.compilations.dto.CompilationDto;
import ru.practicum.ewmsvc.compilations.dto.NewCompilationDto;
import ru.practicum.ewmsvc.compilations.model.Compilation;
import ru.practicum.ewmsvc.event.dto.EventShortDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationMapper {

    public Compilation mapNewDtoToCompilation(NewCompilationDto dto) {
        return new Compilation(
                null,
                dto.getPinned(),
                dto.getTitle()
        );
    }

    public CompilationDto mapToCompilationDto(Compilation compilation, List<EventShortDto> events) {
        return new CompilationDto(
                events,
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }
}
