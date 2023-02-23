package ru.practicum.ewmsvc.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewmsvc.event.repository.EventRepository;
import ru.practicum.ewmsvc.event.model.Event;
import ru.practicum.ewmsvc.request.mapper.RequestMapper;
import ru.practicum.ewmsvc.request.repository.RequestRepository;
import ru.practicum.ewmsvc.request.dto.ParticipationRequestDto;
import ru.practicum.ewmsvc.request.model.Request;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final EventRepository eventRepository;

    @Override
    public List<Request> getByEvent(Long eventId) {
        return requestRepository.getRequestsByEventId(eventId);
    }

    @Override
    public Request getByReqId(Long eventId, Long requestId) {
        return requestRepository.getRequestByEventIdAndId(eventId, requestId);
    }

    @Override
    public Request get(Long id) {
        return requestRepository.getReferenceById(id);
    }

    @Override
    public List<ParticipationRequestDto> getByUserId(Long userId) {
        List<Request> requests = requestRepository.getRequestsByRequesterId(userId);
        return requests.stream()
                .map(requestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(Request request) {
        requestRepository.save(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto save(Long userId, Long eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        if (requestRepository.getRequestByRequesterIdAndEventId(userId, eventId) != null ||
                event.getInitiator().equals(userId) ||
                !event.getState().equals("PUBLISHED") ||
                (event.getConfirmedRequests().equals(event.getParticipantLimit()) && event.getParticipantLimit() != 0)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid membership request conditions");
        }
        Request newRequest = requestMapper.mapDtoToNewRequest(userId, eventId);
        if (event.getRequestModeration().equals(false)) {
            newRequest.setStatus("CONFIRMED");
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        requestRepository.save(newRequest);
        return requestMapper.mapToDto(newRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        Request request = requestRepository.getReferenceById(requestId);
        request.setStatus("CANCELED");
        requestRepository.save(request);
        return requestMapper.mapToDto(request);
    }

    @Override
    public Request getByEventIdAndRequesterId(Long eventId, Long requesterId) {
        return requestRepository.getRequestByRequesterIdAndEventId(requesterId, eventId);
    }
}
