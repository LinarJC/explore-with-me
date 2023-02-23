package ru.practicum.ewmsvc.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewmsvc.event.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    List<Event> getEventsByIdIn(List<Long> idList);

    @Query(nativeQuery = true,
            value = "SELECT id " +
                    "FROM events")
    List<Integer> getAllIds();

    List<Event> findEventsByCategory(Long categoryId);

    Integer countEventsByCategory(Long categoryId);
}
