package ru.practicum.ewm_main.event;

import ru.practicum.ewm_main.event.dto.EventDto;
import ru.practicum.ewm_main.event.dto.NewEventDto;
import ru.practicum.ewm_main.event.dto.ShortEventDto;
import ru.practicum.ewm_main.event.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.ewm_main.category.CategoryMapper.toCategory;
import static ru.practicum.ewm_main.category.CategoryMapper.toCategoryDto;
import static ru.practicum.ewm_main.event.LocationMapper.toLocationDto;
import static ru.practicum.ewm_main.event.model.State.PENDING;
import static ru.practicum.ewm_main.user.UserMapper.toShortUserDto;

public class EventMapper {
    public static EventDto toEventDto(Event event) {
        return EventDto
                .builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .createdOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .initiator(toShortUserDto(event.getInitiator()))
                .location(toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(EventDto eventDto) {
        return Event
                .builder()
                .id(eventDto.getId())
                .annotation(eventDto.getAnnotation())
                .category(toCategory(eventDto.getCategory()))
                .createdOn(LocalDateTime.parse(eventDto.getCreatedOn(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .description(eventDto.getDescription())
                .eventDate(LocalDateTime.parse(eventDto.getEventDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .publishedOn(eventDto.getPublishedOn())
                .requestModeration(eventDto.getRequestModeration())
                .state(eventDto.getState())
                .title(eventDto.getTitle())
                .build();
    }

    public static ShortEventDto toShortEventDto(Event event) {
        return ShortEventDto
                .builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .initiator(toShortUserDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(ShortEventDto shortEventDto) {
        return Event
                .builder()
                .id(shortEventDto.getId())
                .annotation(shortEventDto.getAnnotation())
                .category(toCategory(shortEventDto.getCategory()))
                .eventDate(LocalDateTime.parse(shortEventDto.getEventDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .paid(shortEventDto.getPaid())
                .title(shortEventDto.getTitle())
                .build();
    }

    public static Event toEvent(NewEventDto eventDto) {
        return Event
                .builder()
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .eventDate(LocalDateTime.parse(eventDto.getEventDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.getRequestModeration())
                .title(eventDto.getTitle())
                .state(PENDING)
                .createdOn(LocalDateTime.now())
                .build();
    }
}
