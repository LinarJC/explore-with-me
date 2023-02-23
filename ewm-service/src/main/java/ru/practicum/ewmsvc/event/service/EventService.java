package ru.practicum.ewmsvc.event.service;

import ru.practicum.ewmsvc.event.dto.*;
import ru.practicum.ewmsvc.event.model.Event;
import ru.practicum.ewmsvc.request.dto.ParticipationRequestDto;

import java.util.List;

public interface EventService {
    List<EventShortDto> get(String ip,
                                  String text,
                                  List<Long> categories,
                                  Boolean paid,
                                  String rangeStart,
                                  String rangeEnd,
                                  Boolean onlyAvailable,
                                  String sort,
                                  Integer from,
                                  Integer size);

    List<EventFullDto> get(List<Long> users,
                                 List<String> states,
                                 List<Long> categories,
                                 String rangeStart,
                                 String rangeEnd,
                                 Integer from,
                                 Integer size);

    EventFullDto get(Long id, String ip);

    Event get(Long eventId);

    List<EventShortDto> getByIdsList(List<Long> idList);

    List<EventShortDto> getByUserId(Long userId, Integer from, Integer size);

    EventFullDto update(Long eventId, UpdateEventAdminRequest updatedEvent);

    EventFullDto save(Long userId, NewEventDto newEventDto);

    EventFullDto getByUserIdAndEventId(Long userId, Long eventId);

    EventRequestStatusUpdateResult changeRequestsStatus(
            Long userId, Long eventId, EventRequestStatusUpdateRequest request);

    EventFullDto updateByUser(Long userId, Long eventId, UpdateEventUserRequest updateEvent);

    List<ParticipationRequestDto> getParticipationByUserId(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto declineRequest(Long userId, Long eventId, Long reqId);

    EventFullDto publish(Long eventId);

    EventFullDto reject(Long eventId);

    void exists(List<Long> eventIds);

    EventWithCommentsDto getWithComments(Long id);
}
