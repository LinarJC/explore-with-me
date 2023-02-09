package ru.practicum.ewm_main.compilation.dto;

import lombok.*;
import ru.practicum.ewm_main.event.dto.ShortEventDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private Long id;

    private String title;

    private Boolean pinned;

    private List<ShortEventDto> events;
}
