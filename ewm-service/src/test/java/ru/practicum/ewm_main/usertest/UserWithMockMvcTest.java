package ru.practicum.ewm_main.usertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm_main.user.UserController;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserWithMockMvcTest {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    private UserDto userDto;

    @BeforeEach
    void init() {
        userDto = UserDto
                .builder()
                .id(1L)
                .name("user name")
                .email("user@email.com")
                .build();
    }

    @Test
    void getUsersTest() throws Exception {
        when(userService.getUsers(anyList(), anyInt(), anyInt()))
                .thenReturn(List.of(userDto));
        mvc.perform(get("/admin/users?ids=1&from=0&size=10")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(userDto))));
    }

    @Test
    void createUserTest() throws Exception {
        when(userService.createUser(any()))
                .thenReturn(userDto);
        mvc.perform(post("/admin/users")
                .content(mapper.writeValueAsString(userDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userDto)));
    }
}
