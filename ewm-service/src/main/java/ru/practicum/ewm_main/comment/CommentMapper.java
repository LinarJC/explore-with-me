package ru.practicum.ewm_main.comment;

import ru.practicum.ewm_main.comment.dto.CommentDto;
import ru.practicum.ewm_main.comment.model.Comment;

import java.time.LocalDateTime;

import static ru.practicum.ewm_main.Constant.DATE_TIME_FORMATTER;
import static ru.practicum.ewm_main.comment.model.CommentState.NEW;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getUser().getName())
                .createdOn(comment.getCreatedOn().format(DATE_TIME_FORMATTER))
                .build();
    }

    public static Comment toComment(CommentDto commentDto) {
        return Comment
                .builder()
                .text(commentDto.getText())
                .createdOn(LocalDateTime.now())
                .state(NEW)
                .build();
    }
}
