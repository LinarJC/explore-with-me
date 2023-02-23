package ru.practicum.service;

import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface StatsService {
    void saveEndpointHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, Set<String> uris, boolean unique);
}
