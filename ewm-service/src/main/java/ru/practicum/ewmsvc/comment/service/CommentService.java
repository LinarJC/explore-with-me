package ru.practicum.ewmsvc.comment.service;

import ru.practicum.ewmsvc.comment.dto.CommentDto;
import ru.practicum.ewmsvc.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getByUserId(Long userId);

    List<CommentDto> getByEventId(Long eventId);

    CommentDto save(Long userId, Long eventId, NewCommentDto dto);

    CommentDto update(Long userId, Long commentId, NewCommentDto dto);

    void delete(Long userId, Long commentId);

    CommentDto moderate(Long commentId, NewCommentDto dto);
}
