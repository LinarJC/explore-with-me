package ru.practicum.ewm_main.event.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateEventDto {
    private String annotation;

    private Long category;

    private String description;

    private String eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;
}
