package ru.practicum.ewm_main.participation.service;

import ru.practicum.ewm_main.participation.dto.ParticipationDto;

import java.util.List;

public interface ParticipationService {
    List<ParticipationDto> getParticipationRequests(Long userId);

    ParticipationDto createParticipationRequest(Long userId, Long eventId);

    ParticipationDto cancelParticipationRequest(Long userId, Long reqId);

    List<ParticipationDto> getParticipationRequests(Long eventId, Long userId);

    ParticipationDto confirmParticipationRequest(Long eventId, Long userId, Long reqId);

    ParticipationDto rejectParticipationRequest(Long eventId, Long userId, Long reqId);

}
