package ru.practicum.ewmsvc.event.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.category.service.CategoryService;
import ru.practicum.ewmsvc.category.model.Category;
import ru.practicum.ewmsvc.comment.dto.CommentDto;
import ru.practicum.ewmsvc.event.dto.EventFullDto;
import ru.practicum.ewmsvc.event.dto.EventShortDto;
import ru.practicum.ewmsvc.event.dto.EventWithCommentsDto;
import ru.practicum.ewmsvc.event.dto.NewEventDto;
import ru.practicum.ewmsvc.event.model.Event;
import ru.practicum.ewmsvc.user.service.UserService;
import ru.practicum.ewmsvc.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventMapper {
    private final CategoryService categoryService;
    private final UserService userService;
    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter outdtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public EventFullDto mapToFullDto(Event event, Category category, User user) {
        return new EventFullDto(
                event.getAnnotation(),
                new EventFullDto.CategoryDto(category.getId(), category.getName()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate().format(outdtFormatter),
                event.getId(),
                new EventFullDto.UserShortDto(user.getId(), user.getName()),
                new EventFullDto.Location(event.getLocationLat(), event.getLocationLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public EventFullDto mapToFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                new EventFullDto.CategoryDto(
                        categoryService.get(event.getCategory()).getId(),
                        categoryService.get(event.getCategory()).getName()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate().format(outdtFormatter),
                event.getId(),
                new EventFullDto.UserShortDto(
                        userService.get(event.getInitiator()).getId(),
                        userService.get(event.getInitiator()).getName()),
                new EventFullDto.Location(event.getLocationLat(), event.getLocationLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public EventShortDto mapToShortDto(Event event) {
        return new EventShortDto(
                event.getAnnotation(),
                new EventFullDto.CategoryDto(
                        categoryService.get(event.getCategory()).getId(),
                        categoryService.get(event.getCategory()).getName()),
                event.getConfirmedRequests(),
                event.getEventDate().format(outdtFormatter),
                event.getId(),
                new EventFullDto.UserShortDto(
                        userService.get(event.getInitiator()).getId(),
                        userService.get(event.getInitiator()).getName()),
                event.getPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public Event mapNewToEvent(NewEventDto newEventDto, Long userId) {
        return new Event(
                null,
                newEventDto.getAnnotation(),
                newEventDto.getCategory(),
                0,
                LocalDateTime.now(),
                newEventDto.getDescription(),
                parseLocalDateTime(newEventDto.getEventDate(), dtFormatter),
                userId,
                newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                null,
                newEventDto.getRequestModeration(),
                "PENDING",
                newEventDto.getTitle(),
                0L
        );
    }

    private static LocalDateTime parseLocalDateTime(CharSequence text, DateTimeFormatter dtFormatter) {
        if (text == null) {
            return null;
        }
        return dtFormatter.parse(text, LocalDateTime::from);
    }

    public EventWithCommentsDto mapToEventWithComments(Event event, List<CommentDto> comments) {
        return new EventWithCommentsDto(
                mapToShortDto(event),
                comments
        );
    }

}
