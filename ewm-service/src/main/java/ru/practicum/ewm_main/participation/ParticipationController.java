package ru.practicum.ewm_main.participation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.participation.dto.ParticipationDto;
import ru.practicum.ewm_main.participation.service.ParticipationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
public class ParticipationController {
    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @GetMapping
    public List<ParticipationDto> getParticipationRequests(@PathVariable Long userId) {
        log.info("get user's {} participation requests", userId);
        return participationService.getParticipationRequests(userId);
    }

    @PostMapping
    public ParticipationDto createParticipationRequest(@PathVariable Long userId,
                                                       @RequestParam Long eventId) {
        log.info("create participation request by user {} to event {}", userId, eventId);
        return participationService.createParticipationRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationDto cancelParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long requestId) {
        log.info("cancel participation request {} by user {}", requestId, userId);
        return participationService.cancelParticipationRequest(userId, requestId);
    }
}
