package ru.practicum.ewmsvc.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmsvc.user.service.UserService;
import ru.practicum.ewmsvc.user.dto.NewUserDto;
import ru.practicum.ewmsvc.user.dto.UserDto;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserAdminController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> get(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        log.info("Request endpoint: 'GET /admin/users'");
        return userService.get(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto save(
            @RequestBody NewUserDto newUserDto
    ) {
        log.info("Request endpoint: 'POST /admin/users'");
        return userService.save(newUserDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        log.info("Request endpoint: 'DELETE /admin/users/{}'", userId);
        userService.delete(userId);
    }
}
