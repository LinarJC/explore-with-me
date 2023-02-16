package ru.practicum.ewmsvc.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private String state;
    private String title;
    private Long views;

    @Data
    @AllArgsConstructor
    public static class CategoryDto {
        Long id;
        String name;
    }

    @Data
    @AllArgsConstructor
    public static class UserShortDto {
        Long id;
        String name;
    }

    @Data
    @AllArgsConstructor
    public static class Location {
        Double lat;
        Double lon;
    }
}
