package ru.practicum.ewmsvc.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmsvc.compilations.service.CompilationService;
import ru.practicum.ewmsvc.compilations.service.EventsCompilationsService;
import ru.practicum.ewmsvc.compilations.dto.CompilationDto;
import ru.practicum.ewmsvc.compilations.dto.NewCompilationDto;
import ru.practicum.ewmsvc.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewmsvc.compilations.model.EventsCompilations;
import ru.practicum.ewmsvc.util.Create;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {
    private final CompilationService compilationService;
    private final EventsCompilationsService eventsCompilationsService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompilationDto saveCompilation(
            @Validated({Create.class}) @RequestBody NewCompilationDto compilationDto
    ) {
        log.info("Request endpoint: 'POST /admin/compilations'");
        return compilationService.saveCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Request endpoint: 'DELETE /admin/compilations/{}'", compId);
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @RequestBody UpdateCompilationRequest request) {
        log.info("Request endpoint: 'PATCH /admin/compilations/{}'", compId);
        return compilationService.updateCompilation(compId, request);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(
            @PathVariable Long compId,
            @PathVariable Long eventId
    ) {
        log.info("Request endpoint: 'GET /admin/compilations/{}/events/{}'", compId, eventId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(
            @PathVariable Long compId,
            @PathVariable Long eventId
    ) {
        log.info("Request endpoint: 'GET /admin/compilations/{}/events/{}'", compId, eventId);
        eventsCompilationsService.saveEventCompilation(new EventsCompilations(compId, eventId));
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("Request endpoint: 'GET /admin/compilations/{}/pin'", compId);
        compilationService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        log.info("Request endpoint: 'GET /admin/compilations/{}/pin'", compId);
        compilationService.pinCompilation(compId);
    }
}
