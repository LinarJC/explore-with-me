package ru.practicum.ewm_main.user.service;

import ru.practicum.ewm_main.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);
}
