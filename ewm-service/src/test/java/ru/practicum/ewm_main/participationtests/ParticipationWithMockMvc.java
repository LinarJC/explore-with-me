package ru.practicum.ewm_main.participationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm_main.participation.ParticipationController;
import ru.practicum.ewm_main.participation.dto.ParticipationDto;
import ru.practicum.ewm_main.participation.service.ParticipationService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.ewm_main.participation.model.StatusRequest.PENDING;

@WebMvcTest(controllers = ParticipationController.class)
public class ParticipationWithMockMvc {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ParticipationService participationService;

    @Autowired
    private MockMvc mvc;

    private ParticipationDto participationDto;

    @BeforeEach
    void  init() {
        participationDto = ParticipationDto.builder()
                .id(1L)
                .created(LocalDateTime.now().toString())
                .status(PENDING)
                .requester(1L)
                .event(1L)
                .build();
    }

    @Test
    void getParticipationRequestsTest() throws Exception {
        when(participationService.getParticipationRequests(anyLong()))
                .thenReturn(List.of(participationDto));
        mvc.perform(get("/users/1/requests")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(participationDto))));
    }

    @Test
    void createParticipationRequestTest() throws Exception {
        when(participationService.createParticipationRequest(anyLong(), anyLong()))
                .thenReturn(participationDto);
        mvc.perform(post("/users/1/requests?eventId=1")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(participationDto)));
    }

    @Test
    void cancelParticipationRequestTest() throws Exception {
        when(participationService.cancelParticipationRequest(anyLong(), anyLong()))
                .thenReturn(participationDto);
        mvc.perform(patch("/users/1/requests/1/cancel")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(participationDto)));
    }
}
