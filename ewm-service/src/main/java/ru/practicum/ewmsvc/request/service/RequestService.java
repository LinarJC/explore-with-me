package ru.practicum.ewmsvc.request.service;

import ru.practicum.ewmsvc.request.dto.ParticipationRequestDto;
import ru.practicum.ewmsvc.request.model.Request;

import java.util.List;

public interface RequestService {
    List<Request> getByEvent(Long eventId);

    Request getByReqId(Long eventId, Long reqId);

    void save(Request request);

    Request get(Long id);

    List<ParticipationRequestDto> getByUserId(Long userId);

    ParticipationRequestDto save(Long userId, Long eventId);

    ParticipationRequestDto cancel(Long userId, Long requestId);

    Request getByEventIdAndRequesterId(Long eventId, Long requesterId);

}
