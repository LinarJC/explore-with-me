package ru.practicum.ewm_main.compilation.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.compilation.CompilationMapper;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.dto.ShortCompilationDto;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.compilation.repository.CompilationRepository;
import ru.practicum.ewm_main.event.dto.ShortEventDto;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.exception.NotFoundException;
import ru.practicum.ewm_main.participation.repository.ParticipationRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm_main.participation.model.StatusRequest.CONFIRMED;

@Transactional
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final ParticipationRepository participationRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository, ParticipationRepository participationRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
        this.participationRepository = participationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        if (pinned == null) {
            return compilationRepository.findAll(PageRequest.of(from / size, size))
                    .stream()
                    .map(CompilationMapper::toCompilationDto)
                    .collect(Collectors.toList());
        }
        return compilationRepository.findAllByPinned(pinned, PageRequest.of(from / size, size))
                .stream()
                .map(CompilationMapper::toCompilationDto)
                .map(this::setViewsAndConfirmedRequests)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilation(Long id) {
        return setViewsAndConfirmedRequests(CompilationMapper.toCompilationDto(getAndCheckCompilation(id)));
    }

    @Override
    public CompilationDto createCompilation(ShortCompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(compilationDto);
        List<Event> events = eventRepository.findAllById(compilationDto.getEvents());
        compilation.setEvents(events);
        return setViewsAndConfirmedRequests(CompilationMapper.toCompilationDto(compilationRepository.save(compilation)));
    }

    @Override
    public void deleteCompilation(Long id) {
        compilationRepository.delete(getAndCheckCompilation(id));
    }

    @Override
    public void deleteEventFromCompilation(Long id, Long eventId) {
        Compilation compilation = getAndCheckCompilation(id);
        compilation.getEvents().remove(getAndCheckEvent(eventId));
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(Long id, Long eventId) {
        Compilation compilation = getAndCheckCompilation(id);
        compilation.getEvents().add(getAndCheckEvent(eventId));
        compilationRepository.save(compilation);
    }

    @Override
    public void deleteCompilationFromMainPage(Long id) {
        Compilation compilation = getAndCheckCompilation(id);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void addCompilationToMainPage(Long id) {
        Compilation compilation = getAndCheckCompilation(id);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    private ShortEventDto setConfirmedRequests(ShortEventDto eventDto) {
        eventDto.setConfirmedRequests(participationRepository.countParticipationByEventIdAndStatus(eventDto.getId(),
                CONFIRMED));
        return eventDto;
    }

    private CompilationDto setViewsAndConfirmedRequests(CompilationDto compilationDto) {
        compilationDto.setEvents(compilationDto.getEvents()
                .stream()
                .map(this::setConfirmedRequests)
                .collect(Collectors.toList()));
        return compilationDto;
    }

    private Event getAndCheckEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("event with id = " + id + " not found"));
    }

    private Compilation getAndCheckCompilation(Long id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("compilation with id = " + id + " not found"));
    }
}
