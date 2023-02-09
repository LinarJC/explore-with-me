package ru.practicum.ewm_main.event.service;

import ru.practicum.ewm_main.event.dto.*;

import java.util.List;

public interface EventService {
    List<ShortEventDto> getEvents(String text, List<Long> categoryIds, Boolean paid, String rangeStart, String rangeEnd,
                                  Boolean onlyAvailable, String sort, int from, int size);

    EventDto getEvent(Long id);

    List<ShortEventDto> getUserEvents(Long userId, int from, int size);

    EventDto updateEvent(Long userId, UserUpdateEventDto eventDto);

    EventDto createEvent(Long userId, NewEventDto eventDto);

    EventDto getEventByUser(Long eventId, Long userId);

    EventDto cancelEventByUser(Long eventId, Long userId);

    List<EventDto> getEventsByAdmin(List<Long> userIds, List<String> states, List<Long> categoryIds,
                                    String rangeStart, String rangeEnd, int from, int size);

    EventDto updateEventByAdmin(Long eventId, AdminUpdateEventDto eventDto);

    EventDto publishEvent(Long eventId);

    EventDto rejectEvent(Long eventId);
}
