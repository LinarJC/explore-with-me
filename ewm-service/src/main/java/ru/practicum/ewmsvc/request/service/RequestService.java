package ru.practicum.ewmsvc.request.service;

import ru.practicum.ewmsvc.request.dto.ParticipationRequestDto;
import ru.practicum.ewmsvc.request.model.Request;

import java.util.List;

public interface RequestService {
    List<Request> getRequestsByEvent(Long eventId);

    Request getRequestByReqId(Long eventId, Long reqId);

    void saveRequest(Request request);

    Request getRequest(Long id);

    List<ParticipationRequestDto> getRequestsByUserId(Long userId);

    ParticipationRequestDto saveRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    Request getRequestByEventIdAndRequesterId(Long eventId, Long requesterId);

}
