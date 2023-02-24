package ru.practicum.ewmsvc.comment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.comment.dto.CommentDto;
import ru.practicum.ewmsvc.comment.dto.NewCommentDto;
import ru.practicum.ewmsvc.comment.model.Comment;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentMapper {

    public CommentDto mapToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getUserId(),
                comment.getEventId(),
                comment.getComment(),
                comment.getCreated(),
                comment.getLastChange()
        );
    }

    public Comment dtoToNewComment(NewCommentDto dto, Long userId, Long eventId) {
        return new Comment(
                null,
                userId,
                eventId,
                dto.getComment(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
