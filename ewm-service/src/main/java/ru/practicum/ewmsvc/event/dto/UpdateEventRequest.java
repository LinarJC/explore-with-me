package ru.practicum.ewmsvc.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Integer eventId;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}
