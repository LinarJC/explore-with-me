package ru.practicum.ewmsvc.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewmsvc.user.dto.NewUserDto;
import ru.practicum.ewmsvc.user.dto.UserDto;
import ru.practicum.ewmsvc.user.dto.UserShortDto;
import ru.practicum.ewmsvc.user.mapper.UserMapper;
import ru.practicum.ewmsvc.user.model.User;
import ru.practicum.ewmsvc.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserShortDto get(Long id) {
        return userMapper.mapToUserShortDto(userRepository.getReferenceById(id));
    }

    @Override
    public List<UserDto> get(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        Iterable<User> users;
        if (ids != null) {
            users = userRepository.getUsersByIdIn(ids, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return StreamSupport.stream(users.spliterator(), false)
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto save(NewUserDto newUserDto) {
        if (newUserDto.getName() == null || newUserDto.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uncorrected request");
        }
        if (userRepository.existsUserByName(newUserDto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This name is exist");
        }
        return userMapper.mapToUserDto(
                userRepository.save(
                        userMapper.mapToUser(newUserDto)));
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
