package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<Void> saveHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("Hit with URI = {} save to stats", endpointHitDto.getUri());
        statsService.saveEndpointHit(endpointHitDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStatsDto>> get(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam(required = false) Set<String> uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique) {
        log.info("Get hits statistics");
        return ResponseEntity.status(HttpStatus.OK).body(statsService.get(start, end, uris, unique));
    }
}
