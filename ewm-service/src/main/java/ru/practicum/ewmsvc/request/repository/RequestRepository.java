package ru.practicum.ewmsvc.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmsvc.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> getRequestsByEventId(Long eventId);

    Request getRequestByEventIdAndId(Long eventId, Long requestId);

    List<Request> getRequestsByRequesterId(Long userId);

    Request getRequestByRequesterIdAndEventId(Long userId, Long eventId);
}
