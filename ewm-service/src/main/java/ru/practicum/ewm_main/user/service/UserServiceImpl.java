package ru.practicum.ewm_main.user.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.exception.NotFoundException;
import ru.practicum.ewm_main.user.UserMapper;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        if (ids.isEmpty()) {
            return userRepository.findAll(PageRequest.of(from / size, size))
                    .stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }
        return userRepository.findAllByIdIn(ids, PageRequest.of(from / size, size))
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.delete(checkAndGetUser(id));
    }

    private User checkAndGetUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id = " + id + " not found"));
    }
}
