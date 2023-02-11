package ru.practicum.ewm_main.participationtests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.Constant;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.service.CategoryService;
import ru.practicum.ewm_main.event.dto.EventDto;
import ru.practicum.ewm_main.event.dto.LocationDto;
import ru.practicum.ewm_main.event.dto.NewEventDto;
import ru.practicum.ewm_main.event.service.EventService;
import ru.practicum.ewm_main.exception.BadRequestException;
import ru.practicum.ewm_main.participation.dto.ParticipationDto;
import ru.practicum.ewm_main.participation.service.ParticipationService;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.practicum.ewm_main.participation.model.StatusRequest.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ParticipationServiceTest {
    @Autowired
    private ParticipationService participationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EventService eventService;

    private ParticipationDto participationDto;

    private UserDto userDto;

    private NewEventDto newEventDto;

    private NewCategoryDto newCategoryDto;

    private UserDto userDto2;

    private EventDto eventDto;

    @BeforeEach
    void init() {
        userDto = UserDto.builder().email("email").name("name").build();
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
        newCategoryDto = NewCategoryDto.builder().name("name").build();
        UserDto userDto1 = userService.createUser(userDto);
        CategoryDto categoryDto = categoryService.createCategory(newCategoryDto);
        newEventDto.setCategory(categoryDto.getId());
        eventDto = eventService.createEvent(userDto1.getId(), newEventDto);
        userDto2 = userService.createUser(UserDto.builder().name("name2").email("email2").build());
    }

    @Test
    void createParticipationRequestsWithNotPublishedEventTest() {
        assertThrows(BadRequestException.class, () -> participationService.createParticipationRequest(userDto2.getId(), eventDto.getId()));
    }

    @Test
    void getParticipationRequestsTest() {
        eventService.publishEvent(eventDto.getId());
        participationDto = participationService.createParticipationRequest(userDto2.getId(), eventDto.getId());
        assertEquals(1, participationService.getParticipationRequests(userDto2.getId()).size());
    }

    @Test
    void cancelParticipationRequestTest() {
        eventService.publishEvent(eventDto.getId());
        participationDto = participationService.createParticipationRequest(userDto2.getId(), eventDto.getId());
        participationDto = participationService.cancelParticipationRequest(userDto2.getId(), participationDto.getId());
        assertEquals(CANCELED, participationDto.getStatus());
    }

    @Test
    void getParticipationRequestsByTwoParamTest() {
        eventService.publishEvent(eventDto.getId());
        participationDto = participationService.createParticipationRequest(userDto2.getId(), eventDto.getId());
        assertEquals(1, participationService.getParticipationRequests(eventDto.getId(), eventDto.getInitiator().getId()).size());
    }

    @Test
    void confirmParticipationRequestTest() {
        eventService.publishEvent(eventDto.getId());
        participationDto = participationService.createParticipationRequest(userDto2.getId(), eventDto.getId());
        participationDto = participationService.confirmParticipationRequest(eventDto.getId(), eventDto.getInitiator().getId(),
                participationDto.getId());
        assertEquals(CONFIRMED, participationDto.getStatus());
    }

    @Test
    void rejectParticipationRequestTest() {
        eventService.publishEvent(eventDto.getId());
        participationDto = participationService.createParticipationRequest(userDto2.getId(), eventDto.getId());
        participationDto = participationService.rejectParticipationRequest(eventDto.getId(), eventDto.getInitiator().getId(),
                participationDto.getId());
        assertEquals(REJECTED, participationDto.getStatus());
    }
}
