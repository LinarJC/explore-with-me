package ru.practicum.ewmsvc.event.service;

import ru.practicum.ewmsvc.event.dto.*;
import ru.practicum.ewmsvc.event.model.Event;
import ru.practicum.ewmsvc.request.dto.ParticipationRequestDto;

import java.util.List;

public interface EventService {
    List<EventShortDto> getEvents(String ip,
                                  String text,
                                  List<Long> categories,
                                  Boolean paid,
                                  String rangeStart,
                                  String rangeEnd,
                                  Boolean onlyAvailable,
                                  String sort,
                                  Integer from,
                                  Integer size);

    List<EventFullDto> getEvents(List<Long> users,
                                 List<String> states,
                                 List<Long> categories,
                                 String rangeStart,
                                 String rangeEnd,
                                 Integer from,
                                 Integer size);

    List<EventShortDto> getEventsListByIdsList(List<Long> idList);

    EventFullDto getEvent(Long id, String ip);

    Event getEvent(Long eventId);

    List<EventShortDto> getEventsByUserId(Long userId, Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest updatedEvent);

    EventFullDto saveEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    EventRequestStatusUpdateResult changeRequestsStatus(
            Long userId, Long eventId, EventRequestStatusUpdateRequest request);

    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest updateEvent);

    List<ParticipationRequestDto> getEventParticipationByUserId(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto declineRequest(Long userId, Long eventId, Long reqId);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    void checkEventsExist(List<Long> eventIds);
}
