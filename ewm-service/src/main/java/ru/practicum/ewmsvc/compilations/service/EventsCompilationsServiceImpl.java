package ru.practicum.ewmsvc.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.compilations.model.EventsCompilations;
import ru.practicum.ewmsvc.compilations.model.EventsCompilationsId;
import ru.practicum.ewmsvc.compilations.repository.EventsCompilationsRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsCompilationsServiceImpl implements EventsCompilationsService {
    private final EventsCompilationsRepository eventsCompilationsRepository;

    @Override
    public List<EventsCompilations> get(Long compilationId) {
        return eventsCompilationsRepository.getEventsCompilationsByCompilationId(compilationId);
    }

    @Override
    @Transactional
    public void save(EventsCompilations eventsCompilations) {
        eventsCompilationsRepository.save(eventsCompilations);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        eventsCompilationsRepository.delete(new EventsCompilations(compId, eventId));
    }

    @Override
    @Transactional
    public void delete(List<EventsCompilationsId> ids) {
        eventsCompilationsRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void save(List<EventsCompilations> ecList) {
        eventsCompilationsRepository.saveAll(ecList);
    }
}
