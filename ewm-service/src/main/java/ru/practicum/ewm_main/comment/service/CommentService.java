package ru.practicum.ewm_main.comment.service;

import ru.practicum.ewm_main.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long userId, Long eventId);

    CommentDto updateComment(Long commentId, Long userId, CommentDto commentDto);

    void deleteComment(Long commentId, Long userId);

    List<CommentDto> getAllCommentsByUser(Long userId, int from, int size);

    List<CommentDto> getAllCommentsForEvent(Long eventId, int from, int size);

    CommentDto approveComment(Long commentId);

    CommentDto rejectComment(Long commentId);
}
