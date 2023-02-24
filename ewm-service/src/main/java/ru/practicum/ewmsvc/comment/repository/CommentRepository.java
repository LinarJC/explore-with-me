package ru.practicum.ewmsvc.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmsvc.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getCommentsByUserIdOrderByLastChangeDesc(Long userId);

    List<Comment> getCommentsByEventIdOrderByLastChangeDesc(Long eventId);

    Comment findCommentByUserIdAndEventId(Long userId, Long eventId);
}
