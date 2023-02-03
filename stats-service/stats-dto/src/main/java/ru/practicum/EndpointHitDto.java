package ru.practicum;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EndpointHitDto {
    @NotBlank
    private String app;
    @NotBlank
    private String uri;
    @NotBlank
    private String ip;
    @NotBlank
    private String timestamp;
}
