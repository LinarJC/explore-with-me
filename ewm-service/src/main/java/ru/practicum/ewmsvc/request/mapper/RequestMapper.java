package ru.practicum.ewmsvc.request.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.request.dto.ParticipationRequestDto;
import ru.practicum.ewmsvc.request.model.Request;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestMapper {
    public ParticipationRequestDto mapToDto(Request request) {
        return new ParticipationRequestDto(
                request.getCreated(),
                request.getEventId(),
                request.getId(),
                request.getRequesterId(),
                request.getStatus()
        );
    }

    public Request mapDtoToNewRequest(Long userId, Long eventId) {
        return new Request(
                null,
                LocalDateTime.now(),
                eventId,
                userId,
                "PENDING"
        );
    }
}
