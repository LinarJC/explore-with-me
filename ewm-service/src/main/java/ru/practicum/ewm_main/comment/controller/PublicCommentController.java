package ru.practicum.ewm_main.comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.comment.dto.CommentDto;
import ru.practicum.ewm_main.comment.service.CommentService;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events/{eventId}/comments")
public class PublicCommentController {
    private final CommentService commentService;

    public PublicCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDto> getAllCommentsForEvent(@Positive @PathVariable Long eventId,
                                                   @RequestParam (defaultValue = "0") int from,
                                                   @RequestParam (defaultValue = "10") int size) {
        log.info("get all comments for event {}", eventId);
        return commentService.getAllCommentsForEvent(eventId, from, size);
    }
}
