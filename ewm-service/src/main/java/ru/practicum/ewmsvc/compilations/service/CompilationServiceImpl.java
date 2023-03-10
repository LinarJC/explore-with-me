package ru.practicum.ewmsvc.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.compilations.dto.CompilationDto;
import ru.practicum.ewmsvc.compilations.dto.NewCompilationDto;
import ru.practicum.ewmsvc.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewmsvc.compilations.mapper.CompilationMapper;
import ru.practicum.ewmsvc.compilations.model.Compilation;
import ru.practicum.ewmsvc.compilations.model.EventsCompilations;
import ru.practicum.ewmsvc.compilations.model.EventsCompilationsId;
import ru.practicum.ewmsvc.compilations.repository.CompilationRepository;
import ru.practicum.ewmsvc.event.service.EventService;
import ru.practicum.ewmsvc.event.dto.EventShortDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventsCompilationsService eventsCompilationsService;
    private final EventService eventService;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> get(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.findAll(pageable);
        } else {
            compilations = compilationRepository.getCompilationsByPinned(pinned, pageable);
        }

        List<CompilationDto> compilationDtoList = new ArrayList<>();
        for (Compilation compilation : compilations) {
            List<EventsCompilations> eventsCompilations =
                    eventsCompilationsService.get(compilation.getId());
            List<Long> eventIds = eventsCompilations.stream()
                    .map(EventsCompilations::getEventId)
                    .collect(Collectors.toList());
            List<EventShortDto> events = new ArrayList<>();
            if (eventIds.size() != 0) {
                events = eventService.getByIdsList(eventIds);
            }
            CompilationDto compilationDto = compilationMapper.mapToCompilationDto(compilation, events);
            compilationDtoList.add(compilationDto);
        }
        return compilationDtoList;
    }

    @Override
    public CompilationDto get(Long compId) {
        Compilation compilation = compilationRepository.getReferenceById(compId);

        List<EventsCompilations> eventsCompilations =
                eventsCompilationsService.get(compilation.getId());
        List<Long> eventIds = eventsCompilations.stream()
                .map(EventsCompilations::getEventId)
                .collect(Collectors.toList());
        List<EventShortDto> events = eventService.getByIdsList(eventIds);
        CompilationDto compilationDto = compilationMapper.mapToCompilationDto(compilation, events);

        return compilationDto;
    }

    @Override
    @Transactional
    public CompilationDto save(NewCompilationDto compilationDto) {
        List<Long> eventIds = compilationDto.getEvents();
        eventService.exists(eventIds);
        compilationRepository.save(compilationMapper.mapNewDtoToCompilation(compilationDto));
        Compilation compilation = compilationRepository.getCompilationByTitle(compilationDto.getTitle());

        for (Long eventId : eventIds) {
            eventsCompilationsService.save(
                    new EventsCompilations(compilation.getId(), eventId)
            );
        }
        return get(compilation.getId());
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto update(Long compId, UpdateCompilationRequest request) {
        Compilation compilation = compilationRepository.getReferenceById(compId);
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }
        compilationRepository.save(compilation);

        if (request.getEvents() != null) {
            List<EventsCompilations> eventsComp = eventsCompilationsService.get(compId);
            List<EventsCompilationsId> ids = new ArrayList<>();
            for (EventsCompilations ec : eventsComp) {
                ids.add(new EventsCompilationsId(compId, ec.getEventId()));
            }
            eventsCompilationsService.delete(ids);

            List<EventsCompilations> ecList = new ArrayList<>();
            for (Long id : request.getEvents()) {
                ecList.add(new EventsCompilations(compId, id));
            }
            eventsCompilationsService.save(ecList);
        }
        List<EventShortDto> eventShortDtos = eventService.getByIdsList(request.getEvents());

        return compilationMapper.mapToCompilationDto(compilation, eventShortDtos);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        eventsCompilationsService.deleteEventFromCompilation(compId, eventId);
    }

    @Override
    @Transactional
    public void unpin(Long compId) {
        Compilation compilation = compilationRepository.getReferenceById(compId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void pin(Long compId) {
        Compilation compilation = compilationRepository.getReferenceById(compId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}
