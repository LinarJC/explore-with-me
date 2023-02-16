package ru.practicum.ewmsvc.compilations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(EventsCompilationsId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events_compilations")
public class EventsCompilations {
    @Id
    private Long compilationId;
    @Id
    private Long eventId;
}
