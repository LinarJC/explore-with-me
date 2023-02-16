package ru.practicum.ewmsvc.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.compilations.model.EventsCompilations;
import ru.practicum.ewmsvc.compilations.model.EventsCompilationsId;
import ru.practicum.ewmsvc.compilations.repository.EventsCompilationsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsCompilationsServiceImpl implements EventsCompilationsService {
    private final EventsCompilationsRepository eventsCompilationsRepository;

    @Override
    public List<EventsCompilations> getCompilation(Long compilationId) {
        return eventsCompilationsRepository.getCompilationById(compilationId);
    }

    @Override
    public void saveEventCompilation(EventsCompilations eventsCompilations) {
        eventsCompilationsRepository.save(eventsCompilations);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        eventsCompilationsRepository.delete(new EventsCompilations(compId, eventId));
    }

    @Override
    public void deleteEventsCompilations(List<EventsCompilationsId> ids) {
        eventsCompilationsRepository.deleteAllById(ids);
    }

    @Override
    public void saveListOfEventsCompilations(List<EventsCompilations> ecList) {
        eventsCompilationsRepository.saveAll(ecList);
    }
}
