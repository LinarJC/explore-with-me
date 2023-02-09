package ru.practicum.ewm_main.category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCategoryDto {
    @NotEmpty
    String name;
}
