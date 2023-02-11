package ru.practicum.ewm_main.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
