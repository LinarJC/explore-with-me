package ru.practicum.ewm_main.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.client.EventClient;
import ru.practicum.ewm_main.event.dto.EventDto;
import ru.practicum.ewm_main.event.dto.ShortEventDto;
import ru.practicum.ewm_main.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
public class PublicEventController {
    private final EventService eventService;
    private final EventClient eventClient;

    public PublicEventController(EventService eventService, EventClient eventClient) {
        this.eventService = eventService;
        this.eventClient = eventClient;
    }

    @GetMapping
    public List<ShortEventDto> getEvents(@RequestParam(required = false) String text,
                                         @RequestParam(required = false) List<Long> categoryIds,
                                         @RequestParam(required = false) Boolean paid,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam (defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam (defaultValue = "0") int from,
                                         @RequestParam (defaultValue = "10") int size,
                                         HttpServletRequest httpServletRequest) {
        log.info("get events by param: text = {}, categoryIds = {}, paid = {}, rangeStart = {}, rangeEnd = {}, " +
                "onlyAvailable = {}, sort = {}, from = {}, size = {}", text, categoryIds, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        eventClient.createHit(httpServletRequest);
        return eventService.getEvents(text, categoryIds, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable Long id,
                             HttpServletRequest httpServletRequest) {
        log.info("get event with id {}", id);
        eventClient.createHit(httpServletRequest);
        return eventService.getEvent(id);
    }
}
