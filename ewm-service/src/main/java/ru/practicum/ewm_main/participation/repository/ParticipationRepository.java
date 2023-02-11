package ru.practicum.ewm_main.participation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.participation.model.Participation;
import ru.practicum.ewm_main.participation.model.StatusRequest;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findAllByRequesterId(Long userId);

    Participation findByEventIdAndRequesterId(Long eventId, Long userId);

    int countParticipationByEventIdAndStatus(Long eventId, StatusRequest status);

    List<Participation> findAllByEventId(Long eventId);

    Optional<Participation> findByIdAndRequesterId(Long id, Long userId);
}
