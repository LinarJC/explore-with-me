package ru.practicum.ewmsvc.compilations.service;

import ru.practicum.ewmsvc.compilations.model.EventsCompilations;
import ru.practicum.ewmsvc.compilations.model.EventsCompilationsId;

import java.util.List;

public interface EventsCompilationsService {
    List<EventsCompilations> get(Long compilationId);

    void save(EventsCompilations eventsCompilations);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void delete(List<EventsCompilationsId> ids);

    void save(List<EventsCompilations> ecList);
}
