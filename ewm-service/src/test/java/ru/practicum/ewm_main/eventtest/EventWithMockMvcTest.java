package ru.practicum.ewm_main.eventtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm_main.client.EventClient;
import ru.practicum.ewm_main.event.controller.AdminEventController;
import ru.practicum.ewm_main.event.controller.PublicEventController;
import ru.practicum.ewm_main.event.controller.UserEventController;
import ru.practicum.ewm_main.event.dto.*;
import ru.practicum.ewm_main.event.service.EventService;
import ru.practicum.ewm_main.participation.dto.ParticipationDto;
import ru.practicum.ewm_main.participation.model.StatusRequest;
import ru.practicum.ewm_main.participation.service.ParticipationService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.ewm_main.event.model.State.PENDING;

@WebMvcTest(controllers = {AdminEventController.class, PublicEventController.class, UserEventController.class})
public class EventWithMockMvcTest {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventClient eventClient;

    @MockBean
    private ParticipationService participationService;

    @Autowired
    private MockMvc mvc;

    private EventDto eventDto;

    private ShortEventDto shortEventDto;

    private ParticipationDto participationDto;

    @BeforeEach
    void init() {
        eventDto = EventDto.builder()
                .id(1L)
                .annotation("annotation")
                .createdOn(LocalDateTime.now().toString())
                .description("description")
                .eventDate(LocalDateTime.now().plusDays(5).toString())
                .paid(true)
                .participantLimit(10)
                .requestModeration(true)
                .state(PENDING)
                .title("title")
                .build();

        shortEventDto = ShortEventDto.builder()
                .id(1L)
                .annotation("annotation")
                .eventDate(LocalDateTime.now().plusDays(5).toString())
                .paid(true)
                .title("title")
                .confirmedRequests(10)
                .views(20L)
                .build();

        participationDto = ParticipationDto.builder()
                .id(1L)
                .event(1L)
                .requester(1L)
                .created(LocalDateTime.now().toString())
                .status(StatusRequest.PENDING)
                .build();
    }

    @Test
    void getEventsByAdminTest() throws Exception {
        when(eventService.getEventsByAdmin(anyList(), anyList(), anyList(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(eventDto));
        mvc.perform(get("/admin/events?users=1&categories=1&rangeStart=2023-01-10 00:00:00" +
                        "&rangeEnd=2023-01-30 00:00:00&states=PENDING&from=0&size=10")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(eventDto))));
    }

    @Test
    void updateEventByAdminTest() throws Exception {
        when(eventService.updateEventByAdmin(anyLong(), any()))
                .thenReturn(eventDto);
        mvc.perform(put("/admin/events/1")
                .content(mapper.writeValueAsString(AdminUpdateEventDto.builder().annotation("annotation").build()))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventDto)));
    }

    @Test
    void publishEventTest() throws Exception {
        when(eventService.publishEvent(anyLong()))
                .thenReturn(eventDto);
        mvc.perform(patch("/admin/events/1/publish")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventDto)));
    }

    @Test
    void rejectEventTest() throws Exception {
        when(eventService.rejectEvent(anyLong()))
                .thenReturn(eventDto);
        mvc.perform(patch("/admin/events/1/reject")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventDto)));
    }

    @Test
    void getEventsTest() throws Exception {
        when(eventService.getEvents(anyString(), anyList(), anyBoolean(), anyString(), anyString(), anyBoolean(),
                anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(shortEventDto));
        mvc.perform(get("/events?text=text&categoryIds=1,2&paid=true&rangeStart=2023-01-10 00:00:00" +
                "&rangeEnd=2023-01-30 00:00:00&onlyAvailable=true&sort=VIEWS&from=0&size=10")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(shortEventDto))));
    }

    @Test
    void getEventTest() throws Exception {
        when(eventService.getEvent(anyLong()))
                .thenReturn(eventDto);
        mvc.perform(get("/events/1")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventDto)));
    }

    @Test
    void getUserEventsTest() throws Exception {
        when(eventService.getUserEvents(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(shortEventDto));
        mvc.perform(get("/users/1/events?from=0&size=10")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(shortEventDto))));
    }

    @Test
    void updateEventTest() throws Exception {
        when(eventService.updateEvent(anyLong(), any()))
                .thenReturn(eventDto);
        mvc.perform(patch("/users/1/events")
                .content(mapper.writeValueAsString(UserUpdateEventDto.builder().eventId(1L).annotation("annotation").build()))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventDto)));
    }

    @Test
    void createEventTest() throws Exception {
        when(eventService.createEvent(anyLong(), any()))
                .thenReturn(eventDto);
        mvc.perform(post("/users/1/events")
                .content(mapper.writeValueAsString(NewEventDto.builder()
                        .annotation("annotation")
                        .description("description")
                        .eventDate(LocalDateTime.now().plusDays(5).toString())
                        .title("title")
                        .location(LocationDto.builder().lon(30.25f).lat(21.60f).build())
                        .build()))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventDto)));
    }

    @Test
    void getEventByUserTest() throws Exception {
        when(eventService.getEventByUser(anyLong(), anyLong()))
                .thenReturn(eventDto);
        mvc.perform(get("/users/1/events/1")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventDto)));
    }

    @Test
    void cancelEventByUserTest() throws Exception {
        when(eventService.cancelEventByUser(anyLong(), anyLong()))
                .thenReturn(eventDto);
        mvc.perform(patch("/users/1/events/1")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventDto)));
    }

    @Test
    void getParticipationRequestsTest() throws Exception {
        when(participationService.getParticipationRequests(anyLong(), anyLong()))
                .thenReturn(List.of(participationDto));
        mvc.perform(get("/users/1/events/1/requests")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(participationDto))));
    }

    @Test
    void confirmParticipationRequestTest() throws Exception {
        when(participationService.confirmParticipationRequest(anyLong(), anyLong(), anyLong()))
                .thenReturn(participationDto);
        mvc.perform(patch("/users/1/events/1/requests/1/confirm")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(participationDto)));
    }

    @Test
    void rejectParticipationRequest() throws Exception {
        when(participationService.rejectParticipationRequest(anyLong(), anyLong(), anyLong()))
                .thenReturn(participationDto);
        mvc.perform(patch("/users/1/events/1/requests/1/reject")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(participationDto)));
    }
}
