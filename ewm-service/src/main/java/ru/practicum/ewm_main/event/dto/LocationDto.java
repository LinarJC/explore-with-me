package ru.practicum.ewm_main.event.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private float lat;

    private float lon;
}
