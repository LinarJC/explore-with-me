package ru.practicum.ewm_main.commenttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm_main.comment.controller.AdminCommentController;
import ru.practicum.ewm_main.comment.controller.PublicCommentController;
import ru.practicum.ewm_main.comment.controller.UserCommentController;
import ru.practicum.ewm_main.comment.dto.CommentDto;
import ru.practicum.ewm_main.comment.service.CommentService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AdminCommentController.class, PublicCommentController.class, UserCommentController.class})
public class CommentWithMockMvcTest {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentService commentService;

    @Autowired
    MockMvc mvc;

    private CommentDto commentDto;

    @BeforeEach
    void init() {
        commentDto = CommentDto.builder()
                .id(1L)
                .text("text of comment")
                .createdOn(String.valueOf(LocalDateTime.of(2022, 12, 12, 10, 10, 1)))
                .authorName("name")
                .build();
    }

    @Test
    void approveCommentTest() throws Exception {
        when(commentService.approveComment(anyLong()))
                .thenReturn(commentDto);
        mvc.perform(patch("/admin/comments/1/approve")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentDto)));
    }

    @Test
    void rejectCommentTest() throws Exception {
        when(commentService.rejectComment(anyLong()))
                .thenReturn(commentDto);
        mvc.perform(patch("/admin/comments/1/reject")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentDto)));
    }

    @Test
    void getAllCommentsForEventTest() throws Exception {
        when(commentService.getAllCommentsForEvent(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(commentDto));
        mvc.perform(get("/events/1/comments")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(commentDto))));
    }

    @Test
    void createCommentTest() throws Exception {
        when(commentService.createComment(any(), anyLong(), anyLong()))
                .thenReturn(commentDto);
        mvc.perform(post("/users/1/comments/1")
                .content(mapper.writeValueAsString(commentDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentDto)));
    }

    @Test
    void updateCommentTest() throws Exception {
        when(commentService.updateComment(anyLong(), anyLong(), any()))
                .thenReturn(commentDto);
        mvc.perform(patch("/users/1/comments/1")
                        .content(mapper.writeValueAsString(commentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentDto)));
    }

    @Test
    void getAllCommentsByUserTest() throws Exception {
        when(commentService.getAllCommentsByUser(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(commentDto));
        mvc.perform(get("/users/1/comments?from=0&size=10")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(commentDto))));
    }
}
