package ru.practicum.ewm_main.eventtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.category.model.Category;
import ru.practicum.ewm_main.category.repository.CategoryRepository;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.practicum.ewm_main.event.model.State.PENDING;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    private Event event;

    private Category category;

    private User user;

    @BeforeEach
    void init() {
        event = Event.builder()
                .annotation("annotation")
                .createdOn(LocalDateTime.now())
                .eventDate(LocalDateTime.now().plusDays(5))
                .paid(true)
                .title("title")
                .views(0L)
                .state(PENDING)
                .build();

        category = Category.builder().name("name").build();

        user = User.builder().name("name").email("email").build();
    }

    @Test
    void findAllByCategoryIdTest() {
        Category category1 = categoryRepository.save(category);
        event.setCategory(category1);
        eventRepository.save(event);
        assertThat(eventRepository.findAllByCategoryId(category1.getId()).size(), equalTo(1));
    }

    @Test
    void findAllByInitiatorId() {
        User user1 = userRepository.save(user);
        event.setInitiator(user1);
        eventRepository.save(event);
        Page<Event> events = eventRepository.findAllByInitiatorId(user1.getId(), Pageable.ofSize(10));
        assertThat(events.stream().count(), equalTo(1L));
    }

    @Test
    void searchEventsByAdminTest() {
        User user1 = userRepository.save(user);
        Category category1 = categoryRepository.save(category);
        event.setInitiator(user1);
        event.setCategory(category1);
        eventRepository.save(event);
        Page<Event> events = eventRepository.searchEventsByAdmin(List.of(user1.getId()), List.of(PENDING),
                List.of(category1.getId()), Pageable.ofSize(10));
        assertThat(events.stream().count(), equalTo(1L));
    }

    @Test
    void searchEventsTest() {
        Category category1 = categoryRepository.save(category);
        event.setCategory(category1);
        eventRepository.save(event);
        Page<Event> events = eventRepository.searchEvents("ann", List.of(category1.getId()),
                true, PENDING, Pageable.ofSize(10));
        assertThat(events.stream().count(), equalTo(1L));
    }
}
