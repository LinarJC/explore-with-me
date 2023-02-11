package ru.practicum.ewm_main.event.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateEventDto {
    private Long eventId;

    private String annotation;

    private Long category;

    private String description;

    private String eventDate;

    private Boolean paid;

    private Integer participantLimit;

    private String title;
}
