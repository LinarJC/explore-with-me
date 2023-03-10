package ru.practicum.ewmsvc.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.comment.dto.CommentDto;
import ru.practicum.ewmsvc.comment.dto.NewCommentDto;
import ru.practicum.ewmsvc.comment.mapper.CommentMapper;
import ru.practicum.ewmsvc.comment.model.Comment;
import ru.practicum.ewmsvc.comment.repository.CommentRepository;
import ru.practicum.ewmsvc.request.service.RequestService;
import ru.practicum.ewmsvc.request.model.Request;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final RequestService requestService;

    @Override
    public List<CommentDto> getByUserId(Long userId) {
        List<Comment> comments = commentRepository.getCommentsByUserIdOrderByLastChangeDesc(userId);
        return comments.stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getByEventId(Long eventId) {
        List<Comment> comments = commentRepository.getCommentsByEventIdOrderByLastChangeDesc(eventId);
        return comments.stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto save(Long userId, Long eventId, NewCommentDto dto) {
        Request request = requestService.getByEventIdAndRequesterId(eventId, userId);
        if (request == null || !request.getStatus().equals("CONFIRMED")) {
            throw new ValidationException("The user did not participate in the event");
        }
        if (commentRepository.findCommentByUserIdAndEventId(userId, eventId) != null) {
            throw new ValidationException("The comment already exists, go to the update method");
        }
        Comment comment = commentMapper.dtoToNewComment(dto, userId, eventId);
        return commentMapper.mapToDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto update(Long userId, Long commentId, NewCommentDto dto) {
        Comment comment = commentRepository.getReferenceById(commentId);
        if (!comment.getUserId().equals(userId)) {
            throw new ValidationException("The comment cannot be updated");
        }
        comment.setComment(dto.getComment());
        comment.setLastChange(LocalDateTime.now());
        return commentMapper.mapToDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void delete(Long userId, Long commentId) {
        Comment comment = commentRepository.getReferenceById(commentId);
        if (!comment.getUserId().equals(userId)) {
            throw new ValidationException("You can't delete a comment");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public CommentDto moderate(Long commentId, NewCommentDto dto) {
        Comment comment = commentRepository.getReferenceById(commentId);
        comment.setComment(dto.getComment());
        comment.setLastChange(LocalDateTime.now());
        return commentMapper.mapToDto(commentRepository.save(comment));
    }
}
