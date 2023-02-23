package ru.practicum.ewmsvc.user.service;

import ru.practicum.ewmsvc.user.dto.NewUserDto;
import ru.practicum.ewmsvc.user.dto.UserDto;
import ru.practicum.ewmsvc.user.dto.UserShortDto;

import java.util.List;

public interface UserService {
    UserShortDto get(Long id);

    List<UserDto> get(List<Long> ids, Integer from, Integer size);

    UserDto save(NewUserDto newUserDto);

    void delete(Long userId);
}
