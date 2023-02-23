package ru.practicum.ewmsvc.compilations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmsvc.compilations.model.EventsCompilations;
import ru.practicum.ewmsvc.compilations.model.EventsCompilationsId;

import java.util.List;

public interface EventsCompilationsRepository extends JpaRepository<EventsCompilations, EventsCompilationsId> {
    List<EventsCompilations> getEventsCompilationsByCompilationId(Long compilationId);
}
