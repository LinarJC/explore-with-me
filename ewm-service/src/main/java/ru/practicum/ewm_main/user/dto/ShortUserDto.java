package ru.practicum.ewm_main.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUserDto {
    private Long id;

    private String name;
}
