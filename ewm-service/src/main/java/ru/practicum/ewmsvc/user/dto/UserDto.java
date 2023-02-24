package ru.practicum.ewmsvc.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    String email;
    Long id;
    String name;
}
