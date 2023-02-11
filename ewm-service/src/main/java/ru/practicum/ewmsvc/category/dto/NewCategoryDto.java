package ru.practicum.ewmsvc.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmsvc.util.Create;
import ru.practicum.ewmsvc.util.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotBlank(groups = {Create.class, Update.class})
    @NotNull(groups = {Create.class, Update.class})
    String name;
}
