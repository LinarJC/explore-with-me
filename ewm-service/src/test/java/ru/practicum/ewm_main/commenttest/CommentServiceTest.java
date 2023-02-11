package ru.practicum.ewm_main.commenttest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.Constant;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.service.CategoryService;
import ru.practicum.ewm_main.comment.dto.CommentDto;
import ru.practicum.ewm_main.comment.repository.CommentRepository;
import ru.practicum.ewm_main.comment.service.CommentService;
import ru.practicum.ewm_main.event.dto.EventDto;
import ru.practicum.ewm_main.event.dto.LocationDto;
import ru.practicum.ewm_main.event.dto.NewEventDto;
import ru.practicum.ewm_main.event.service.EventService;
import ru.practicum.ewm_main.exception.BadRequestException;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.practicum.ewm_main.comment.model.CommentState.REJECTED;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentRepository commentRepository;

    private CommentDto commentDto;

    private UserDto userDto;

    private NewEventDto newEventDto;

    private NewCategoryDto categoryDto;

    @BeforeEach
    void init() {
        commentDto = CommentDto.builder().text("text").build();
        userDto = UserDto.builder().email("email").name("name").build();
        newEventDto = NewEventDto.builder()
                .annotation("annotation")
                .eventDate(LocalDateTime.now().plusDays(5).format(Constant.DATE_TIME_FORMATTER))
                .paid(true)
                .title("title")
                .description("description")
                .participantLimit(10)
                .requestModeration(false)
                .location(LocationDto.builder().lon(20.20f).lat(10.10f).build())
                .build();
        categoryDto = NewCategoryDto.builder().name("name").build();
    }

    @Test
    void createCommentTest() {
        UserDto userDto1 = userService.createUser(userDto);
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        newEventDto.setCategory(categoryDto1.getId());
        EventDto eventDto = eventService.createEvent(userDto1.getId(), newEventDto);
        CommentDto commentDto1 = commentService.createComment(commentDto, userDto1.getId(), eventDto.getId());
        assertEquals("text", commentDto1.getText());
    }

    @Test
    void updateCommentTest() {
        UserDto userDto1 = userService.createUser(userDto);
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        newEventDto.setCategory(categoryDto1.getId());
        EventDto eventDto = eventService.createEvent(userDto1.getId(), newEventDto);
        CommentDto commentDto1 = commentService.createComment(commentDto, userDto1.getId(), eventDto.getId());
        CommentDto updateCom = commentService.updateComment(commentDto1.getId(), userDto1.getId(), CommentDto.builder().text("newText").build());
        assertEquals("newText", updateCom.getText());
    }

    @Test
    void updateCommentWithWrongIdTest() {
        assertThrows(BadRequestException.class, () -> commentService.updateComment(1L, 1L, commentDto));
    }

    @Test
    void deleteCommentTest() {
        UserDto userDto1 = userService.createUser(userDto);
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        newEventDto.setCategory(categoryDto1.getId());
        EventDto eventDto = eventService.createEvent(userDto1.getId(), newEventDto);
        CommentDto commentDto1 = commentService.createComment(commentDto, userDto1.getId(), eventDto.getId());
        commentService.approveComment(commentDto1.getId());
        assertEquals(1, commentService.getAllCommentsForEvent(eventDto.getId(), 0, 10).size());
        commentService.deleteComment(commentDto1.getId(), userDto1.getId());
        assertEquals(0, commentService.getAllCommentsByUser(userDto1.getId(), 0, 10).size());
    }

    @Test
    void rejectCommentTest() {
        UserDto userDto1 = userService.createUser(userDto);
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        newEventDto.setCategory(categoryDto1.getId());
        EventDto eventDto = eventService.createEvent(userDto1.getId(), newEventDto);
        CommentDto commentDto1 = commentService.createComment(commentDto, userDto1.getId(), eventDto.getId());
        commentService.rejectComment(commentDto1.getId());
        assertEquals(REJECTED, commentRepository.findById(commentDto1.getId()).get().getState());
    }
}
