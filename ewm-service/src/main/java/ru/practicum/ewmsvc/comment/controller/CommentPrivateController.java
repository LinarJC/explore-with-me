package ru.practicum.ewmsvc.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewmsvc.comment.repository.CommentRepository;
import ru.practicum.ewmsvc.comment.service.CommentService;
import ru.practicum.ewmsvc.comment.dto.CommentDto;
import ru.practicum.ewmsvc.comment.dto.NewCommentDto;
import ru.practicum.ewmsvc.request.model.Request;
import ru.practicum.ewmsvc.request.service.RequestService;
import ru.practicum.ewmsvc.util.Create;
import ru.practicum.ewmsvc.util.Update;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/comments")
public class CommentPrivateController {
    private final CommentService commentService;
    private final RequestService requestService;
    private final CommentRepository commentRepository;

    @PostMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto saveComment(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Validated({Create.class}) @RequestBody NewCommentDto dto
    ) {
        Request request = requestService.getRequestByEventIdAndRequesterId(eventId, userId);
        if (request == null || !request.getStatus().equals("CONFIRMED")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The user did not participate in the event");
        }
        if (commentRepository.findCommentByUserIdAndEventId(userId, eventId) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "The comment already exists, go to the update method");
        }
        log.info("Adding a new comment by the user {} to the event {}", userId, eventId);
        return commentService.saveComment(userId, eventId, dto);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(
            @PathVariable Long userId,
            @PathVariable Long commentId,
            @Validated({Update.class}) @RequestBody NewCommentDto dto
    ) {
        if (!commentRepository.getReferenceById(commentId).getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The comment cannot be updated");
        }
        log.info("Editing a comment {} by a user {}", commentId, userId);
        return commentService.updateComment(userId, commentId, dto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(
            @PathVariable Long userId,
            @PathVariable Long commentId
    ) {
        if (!commentRepository.getReferenceById(commentId).getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't delete a comment");
        }
        log.info("Editing a comment {} by a user {}", commentId, userId);
        commentService.deleteComment(userId, commentId);
    }
}
