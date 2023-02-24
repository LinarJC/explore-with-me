package ru.practicum.ewmsvc.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmsvc.request.dto.ParticipationRequestDto;
import ru.practicum.ewmsvc.request.service.RequestService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class RequestController {
    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getByUserId(
            @PathVariable Long userId
    ) {
        log.info("Request endpoint: 'GET /users/{}/requests')", userId);
        return requestService.getByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ParticipationRequestDto save(
            @PathVariable Long userId,
            @RequestParam Long eventId
    ) {
        log.info("Request endpoint: 'POST /users/{}/requests'", userId);
        return requestService.save(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        log.info("Request endpoint: 'PATCH /users/{}/requests/{}/cancel'", userId, requestId);
        return requestService.cancel(userId, requestId);
    }
}
