package ru.practicum.ewm_main.commenttest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.category.model.Category;
import ru.practicum.ewm_main.category.repository.CategoryRepository;
import ru.practicum.ewm_main.comment.model.Comment;
import ru.practicum.ewm_main.comment.repository.CommentRepository;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.ewm_main.comment.model.CommentState.NEW;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByEventAndStateTest() {
        Category category = categoryRepository.save(Category.builder().name("category").build());
        Event event = eventRepository.save(Event.builder()
                .annotation("annotation")
                .category(category)
                .createdOn(LocalDateTime.now())
                .description("description")
                .eventDate(LocalDateTime.now().plusDays(5))
                .paid(true)
                .title("title")
                .build());
        commentRepository.save(Comment.builder()
                .text("text").state(NEW).event(event).createdOn(LocalDateTime.now()).build());
        Page<Comment> comments = commentRepository.findAllByEventAndState(event, NEW, Pageable.ofSize(10));
        assertThat(comments.stream().count(), equalTo(1L));
    }

    @Test
    void findAllByUserTest() {
        User user = userRepository.save(User.builder().name("name").email("email").build());
        commentRepository.save(Comment.builder()
                .text("text").state(NEW).user(user).createdOn(LocalDateTime.now()).build());
        Page<Comment> comments = commentRepository.findAllByUser(user, Pageable.ofSize(10));
        assertThat(comments.stream().count(), equalTo(1L));
    }

    @Test
    void findByIdAndUserIdTest() {
        User user = userRepository.save(User.builder().name("name").email("email").build());
        commentRepository.save(Comment.builder()
                .text("text").state(NEW).user(user).createdOn(LocalDateTime.now()).build());
        assertNotNull(commentRepository.findByIdAndUserId(1L, 1L).get());
    }
}
