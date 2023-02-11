package ru.practicum.ewm_main.participationtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewm_main.participation.dto.ParticipationDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.practicum.ewm_main.participation.model.StatusRequest.PENDING;

@JsonTest
public class ParticipationDtoJsonTest {
    @Autowired
    private JacksonTester<ParticipationDto> json;

    @Test
    void testParticipationDto() throws IOException {
        ParticipationDto participationDto = ParticipationDto.builder()
                .id(1L)
                .status(PENDING)
                .requester(1L)
                .created(LocalDateTime.now().toString())
                .build();

        JsonContent<ParticipationDto> result = json.write(participationDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(PENDING.toString());
        assertThat(result).extractingJsonPathNumberValue("$.requester").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(participationDto.getCreated());
    }
}
