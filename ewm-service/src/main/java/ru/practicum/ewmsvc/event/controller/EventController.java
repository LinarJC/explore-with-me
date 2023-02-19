package ru.practicum.ewmsvc.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmsvc.event.service.EventService;
import ru.practicum.ewmsvc.event.dto.EventFullDto;
import ru.practicum.ewmsvc.event.dto.EventShortDto;
import ru.practicum.ewmsvc.event.dto.EventWithCommentsDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventController {
    private final EventService eventService;
    private final HttpServletRequest request;

    @GetMapping
    public List<EventShortDto> getEvents(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "paid", required = false) Boolean paid,
            @RequestParam(name = "rangeStart", required = false) String rangeStart,
            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        log.info("Request endpoint: 'GET /events'");
        String ip = request.getRemoteAddr();
        return eventService.getEvents(ip, text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Long id) {
        log.info("Request endpoint: 'GET /events/{}", id);
        String ip = request.getRemoteAddr();
        return eventService.getEvent(id, ip);
    }

    @GetMapping("/{id}/comments")
    public EventWithCommentsDto getEventWithComments(@PathVariable Long id) {
        log.info("Request endpoint: 'GET /events/{} (Getting an event by id with all comments)", id);
        return eventService.getEventWithComments(id);
    }
}
