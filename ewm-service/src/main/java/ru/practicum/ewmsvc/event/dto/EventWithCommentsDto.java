package ru.practicum.ewmsvc.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewmsvc.comment.dto.CommentDto;

import java.util.List;

@Data
@AllArgsConstructor
public class EventWithCommentsDto {
    private EventShortDto eventShortDto;
    private List<CommentDto> comments;
}
