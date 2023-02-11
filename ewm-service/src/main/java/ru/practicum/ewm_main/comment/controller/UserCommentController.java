package ru.practicum.ewm_main.comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.comment.dto.CommentDto;
import ru.practicum.ewm_main.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/comments")
public class UserCommentController {
    private final CommentService commentService;

    public UserCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{eventId}")
    public CommentDto createComment(@Valid @RequestBody CommentDto commentDto,
                                    @Positive @PathVariable Long userId,
                                    @Positive @PathVariable Long eventId) {
        log.info("create comment by user {}", userId);
        return commentService.createComment(commentDto, userId, eventId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@Positive @PathVariable Long commentId,
                                    @Positive @PathVariable Long userId,
                                    @Valid @RequestBody CommentDto commentDto) {
        log.info("update comment {}", commentId);
        return commentService.updateComment(commentId, userId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@Positive @PathVariable Long userId,
                              @Positive @PathVariable Long commentId) {
        log.info("delete comment {}", commentId);
        commentService.deleteComment(commentId, userId);
    }

    @GetMapping
    public List<CommentDto> getAllCommentsByUser(@Positive @PathVariable Long userId,
                                                 @RequestParam (defaultValue = "0") int from,
                                                 @RequestParam (defaultValue = "10") int size) {
        log.info("get all user {} comments", userId);
        return commentService.getAllCommentsByUser(userId, from, size);
    }
}
