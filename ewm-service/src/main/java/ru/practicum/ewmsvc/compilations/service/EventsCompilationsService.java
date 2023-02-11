package ru.practicum.ewmsvc.compilations.service;

import ru.practicum.ewmsvc.compilations.model.EventsCompilations;
import ru.practicum.ewmsvc.compilations.model.EventsCompilationsId;

import java.util.List;

public interface EventsCompilationsService {
    List<EventsCompilations> getCompilation(Long compilationId);

    void saveEventCompilation(EventsCompilations eventsCompilations);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void deleteEventsCompilations(List<EventsCompilationsId> ids);

    void saveListOfEventsCompilations(List<EventsCompilations> ecList);
}
