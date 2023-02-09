package ru.practicum.ewm_main.participation.model;

import lombok.*;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "participations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester")
    private User requester;

    @Enumerated(EnumType.STRING)
    private StatusRequest status;
}