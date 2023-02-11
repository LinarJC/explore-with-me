package ru.practicum.ewm_main.usertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void init() {
        userDto = UserDto.builder().name("name").email("email").build();
    }

    @Test
    void getUsersTest() {
        userService.createUser(userDto);
        assertEquals(1, userService.getUsers(Collections.emptyList(), 0, 10).size());
        assertEquals(0, userService.getUsers(List.of(2L), 0, 10).size());
    }

    @Test
    void deleteUserTest() {
        UserDto userDto1 = userService.createUser(userDto);
        assertEquals(1, userService.getUsers(List.of(1L), 0, 10).size());
        userService.deleteUser(userDto1.getId());
        assertEquals(0, userService.getUsers(Collections.emptyList(), 0, 10).size());
    }
}
