package ru.practicum.ewmsvc.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmsvc.comment.service.CommentService;
import ru.practicum.ewmsvc.comment.dto.CommentDto;
import ru.practicum.ewmsvc.comment.dto.NewCommentDto;
import ru.practicum.ewmsvc.util.Update;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/comments")
public class CommentAdminController {
    private final CommentService commentService;

    @PatchMapping("/{commentId}")
    public CommentDto moderate(@PathVariable Long commentId, @Validated({Update.class}) @RequestBody NewCommentDto dto
    ) {
        log.info("Comment moderation {}", commentId);
        return commentService.moderate(commentId, dto);
    }
}
