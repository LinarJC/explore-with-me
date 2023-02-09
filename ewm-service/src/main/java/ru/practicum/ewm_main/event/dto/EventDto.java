package ru.practicum.ewm_main.event.dto;

import lombok.*;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.event.model.State;
import ru.practicum.ewm_main.user.dto.ShortUserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;

    private String annotation;

    private CategoryDto category;

    private String createdOn;

    private String description;

    private String eventDate;

    private ShortUserDto initiator;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    private String title;

    private Integer confirmedRequests;

    private Long views;
}
