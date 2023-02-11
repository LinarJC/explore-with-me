package ru.practicum.ewmsvc.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmsvc.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM requests " +
                    "WHERE event = ?")
    List<Request> getRequestsByEvent(Long eventId);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM requests " +
                    "WHERE event = ? AND id = ?")
    Request getRequestsByReqId(Long eventId, Long reqId);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM requests " +
                    "WHERE requester = ?")
    List<Request> getRequestsByUserId(Long userId);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM requests " +
                    "WHERE requester = ? " +
                    "AND event = ?")
    Request getRequestByUserIdAndEventId(Long userId, Long eventId);
}
