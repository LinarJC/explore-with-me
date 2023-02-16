package ru.practicum.ewmsvc.user.service;

import ru.practicum.ewmsvc.user.dto.NewUserDto;
import ru.practicum.ewmsvc.user.dto.UserDto;
import ru.practicum.ewmsvc.user.dto.UserShortDto;

import java.util.List;

public interface UserService {
    UserShortDto getUser(Long id);

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto saveUser(NewUserDto newUserDto);

    void deleteUser(Long userId);
}
