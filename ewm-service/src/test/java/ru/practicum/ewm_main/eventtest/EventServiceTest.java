package ru.practicum.ewm_main.eventtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.Constant;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.service.CategoryService;
import ru.practicum.ewm_main.event.dto.*;
import ru.practicum.ewm_main.event.service.EventService;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.ewm_main.event.model.State.CANCELED;
import static ru.practicum.ewm_main.event.model.State.PENDING;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventServiceTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    private NewEventDto newEventDto;

    private NewCategoryDto newCategoryDto;

    private UserDto userDto;

    private EventDto eventDto;

    @BeforeEach
    void init() {
        userDto = UserDto.builder().email("email").name("name").build();
        newCategoryDto = NewCategoryDto.builder().name("name").build();
        newEventDto = NewEventDto.builder()
                .annotation("annotation")
                .eventDate(LocalDateTime.now().plusDays(5).format(Constant.DATE_TIME_FORMATTER))
                .paid(true)
                .title("title")
                .description("description")
                .participantLimit(10)
                .requestModeration(true)
                .location(LocationDto.builder().lon(20.20f).lat(10.10f).build())
                .build();
        userDto = userService.createUser(userDto);
        CategoryDto categoryDto = categoryService.createCategory(newCategoryDto);
        newEventDto.setCategory(categoryDto.getId());
        eventDto = eventService.createEvent(userDto.getId(), newEventDto);
    }

    @Test
    void getEventsTest() {
        eventService.publishEvent(eventDto.getId());
        assertEquals(1, eventService.getEvents("desc", null, true, null, null,
                false, null, 0, 10).size());
    }

    @Test
    void getEventTest() {
        eventService.publishEvent(eventDto.getId());
        assertNotNull(eventService.getEvent(eventDto.getId()));
    }

    @Test
    void getUserEventsTest() {
        assertEquals(1, eventService.getUserEvents(userDto.getId(), 0, 10).size());
    }

    @Test
    void updateEventTest() {
        eventService.rejectEvent(eventDto.getId());
        EventDto eventDto1 = eventService.updateEvent(userDto.getId(), UserUpdateEventDto.builder()
                .eventId(eventDto.getId()).annotation("newAnnotation").build());
        assertEquals("newAnnotation", eventDto1.getAnnotation());
        assertEquals(PENDING, eventDto1.getState());
    }

    @Test
    void getEventByUserTest() {
        assertNotNull(eventService.getEventByUser(eventDto.getId(), userDto.getId()));
    }

    @Test
    void cancelEventByUserTest() {
        assertEquals(PENDING, eventService.getEventByUser(eventDto.getId(), userDto.getId()).getState());
        eventService.cancelEventByUser(eventDto.getId(), userDto.getId());
        assertEquals(CANCELED, eventService.getEventByUser(eventDto.getId(), userDto.getId()).getState());
    }

    @Test
    void getEventsByAdminTest() {
        assertEquals(1, eventService.getEventsByAdmin(null, null, null,
                null, null, 0, 10).size());
        assertEquals(0, eventService.getEventsByAdmin(List.of(21L), List.of(PENDING.toString()),
                List.of(1L), null, null, 0, 10).size());
    }

    @Test
    void updateEventByAdminTest() {
        EventDto eventDto1 = eventService.updateEventByAdmin(eventDto.getId(), AdminUpdateEventDto.builder()
                .annotation("newAnnotation").title("newTitle").build());
        assertEquals("newAnnotation", eventDto1.getAnnotation());
        assertEquals("newTitle", eventDto1.getTitle());
    }
}
