package ru.practicum.ewmsvc.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmsvc.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM comments " +
                    "WHERE user_id = ?1 " +
                    "ORDER BY last_change DESC ")
    List<Comment> getCommentsByUserId(Long userId);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM comments " +
                    "WHERE event_id = ?1 " +
                    "ORDER BY last_change DESC ")
    List<Comment> getCommentsByEventId(Long eventId);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM comments " +
                    "WHERE user_id = ? AND event_id = ?")
    Comment findCommentByUserIdAndEventId(Long userId, Long eventId);
}
