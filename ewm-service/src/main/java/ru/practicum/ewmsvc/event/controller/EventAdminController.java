package ru.practicum.ewmsvc.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmsvc.event.service.EventService;
import ru.practicum.ewmsvc.event.dto.EventFullDto;
import ru.practicum.ewmsvc.event.dto.UpdateEventAdminRequest;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class EventAdminController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> get(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        log.info("Request endpoint: 'GET /events'");
        return eventService.get(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(
            @PathVariable Long eventId,
            @RequestBody UpdateEventAdminRequest updatedEvent) {
        log.info("Request endpoint: 'PUT /admin/events/{}'", eventId);
        return eventService.update(eventId, updatedEvent);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publish(@PathVariable Long eventId) {
        log.info("Request endpoint: 'PATCH /admin/events/{}/publish'", eventId);
        return eventService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto reject(@PathVariable Long eventId) {
        log.info("Request endpoint: 'GET /admin/events/{}/reject'", eventId);
        return eventService.reject(eventId);
    }
}
