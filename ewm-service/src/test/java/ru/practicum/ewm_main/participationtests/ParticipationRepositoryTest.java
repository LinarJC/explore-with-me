package ru.practicum.ewm_main.participationtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.category.model.Category;
import ru.practicum.ewm_main.category.repository.CategoryRepository;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.participation.model.Participation;
import ru.practicum.ewm_main.participation.repository.ParticipationRepository;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.ewm_main.participation.model.StatusRequest.PENDING;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ParticipationRepositoryTest {
    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAllByRequesterId() {
        User user = userRepository.save(User.builder().name("name").email("email").build());
        participationRepository.save(Participation.builder().created(LocalDateTime.now()).requester(user).status(PENDING).build());
        assertThat(participationRepository.findAllByRequesterId(user.getId()).size(), equalTo(1));
    }

    @Test
    void findByEventIdAndRequesterIdTest() {
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
        User user = userRepository.save(User.builder().name("name").email("email").build());
        participationRepository.save(Participation.builder()
                .created(LocalDateTime.now()).requester(user).status(PENDING).event(event).build());
        assertNotNull(participationRepository.findByEventIdAndRequesterId(event.getId(), user.getId()));
    }

    @Test
    void countParticipationByEventIdAndStatusTest() {
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
        participationRepository.save(Participation.builder()
                .created(LocalDateTime.now()).status(PENDING).event(event).build());
        assertThat(participationRepository.countParticipationByEventIdAndStatus(event.getId(), PENDING), equalTo(1));
    }

    @Test
    void findAllByEventIdAndEventInitiatorIdTest() {
        Category category = categoryRepository.save(Category.builder().name("category").build());
        User user = userRepository.save(User.builder().name("name").email("email").build());
        Event event = eventRepository.save(Event.builder()
                .annotation("annotation")
                .category(category)
                .createdOn(LocalDateTime.now())
                .description("description")
                .eventDate(LocalDateTime.now().plusDays(5))
                .initiator(user)
                .paid(true)
                .title("title")
                .build());
        participationRepository.save(Participation.builder()
                .created(LocalDateTime.now()).status(PENDING).event(event).build());
        assertNotNull(participationRepository.findAllByEventIdAndEventInitiatorId(event.getId(), user.getId()));
    }

    @Test
    void findByIdAndRequesterIdTest() {
        User user = userRepository.save(User.builder().email("email").name("name").build());
        participationRepository.save(Participation.builder()
                .created(LocalDateTime.now()).requester(user).status(PENDING).build());
        assertNotNull(participationRepository.findByIdAndRequesterId(1L, user.getId()));
    }
}
