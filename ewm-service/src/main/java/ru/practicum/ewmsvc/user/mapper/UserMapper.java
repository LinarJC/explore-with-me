package ru.practicum.ewmsvc.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmsvc.user.dto.NewUserDto;
import ru.practicum.ewmsvc.user.dto.UserDto;
import ru.practicum.ewmsvc.user.dto.UserShortDto;
import ru.practicum.ewmsvc.user.model.User;

@Service
@RequiredArgsConstructor
public class UserMapper {
    public UserShortDto mapToUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getId(),
                user.getName()
        );
    }

    public User mapToUser(NewUserDto userDto) {
        return new User(
                null,
                userDto.getEmail(),
                userDto.getName()
        );
    }
}
