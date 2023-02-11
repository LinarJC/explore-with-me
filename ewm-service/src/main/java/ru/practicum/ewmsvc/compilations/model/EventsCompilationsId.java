package ru.practicum.ewmsvc.compilations.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EventsCompilationsId implements Serializable {
    private Long compilationId;
    private Long eventId;

    public EventsCompilationsId(Long compilationId, Long eventId) {
        this.compilationId = compilationId;
        this.eventId = eventId;
    }

    public EventsCompilationsId() {

    }
}
