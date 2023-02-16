package ru.practicum.ewmsvc.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmsvc.event.service.EventService;
import ru.practicum.ewmsvc.event.dto.*;
import ru.practicum.ewmsvc.request.dto.ParticipationRequestDto;
import ru.practicum.ewmsvc.util.Create;
import ru.practicum.ewmsvc.util.Update;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class EventPrivateController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        log.info("Request endpoint: 'GET /users/{}/events'", userId);
        return eventService.getEventsByUserId(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto saveEvent(
            @PathVariable Long userId,
            @Validated({Create.class}) @RequestBody NewEventDto newEventDto
    ) {
        log.info("Request endpoint: 'POST /users/{}/events'", userId);
        return eventService.saveEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByUserIdAndEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.info("Request endpoint: 'GET /users/{}/events/{}'", userId, eventId);
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Validated({Update.class}) @RequestBody UpdateEventUserRequest updateEvent
    ) {
        log.info("Request endpoint: 'PATCH /users/{}/events/{}'", userId, eventId);
        return eventService.updateEventByUser(userId, eventId, updateEvent);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventParticipationByUserId(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.info("Request endpoint: 'GET /users/{}/events/{}/requests'", userId, eventId);
        return eventService.getEventParticipationByUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestsStatus(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody EventRequestStatusUpdateRequest request
    ) {
        return eventService.changeRequestsStatus(userId, eventId, request);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId
    ) {
        log.info("Request endpoint: 'PATCH /users/{}/events/{}/requests/{}/confirm'", userId, eventId, reqId);
        return eventService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto declineRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId
    ) {
        log.info("Request endpoint: 'PATCH /users/{}/events/{}/requests/{}/reject'", userId, eventId, reqId);
        return eventService.declineRequest(userId, eventId, reqId);
    }
}
