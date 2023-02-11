package ru.practicum.ewm_main.eventtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewm_main.event.dto.EventDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.practicum.ewm_main.event.model.State.PENDING;

@JsonTest
public class EventDtoJsonTest {
    @Autowired
    private JacksonTester<EventDto> json;

    @Test
    void testEventDto() throws IOException {
        EventDto eventDto = EventDto.builder()
                .id(1L)
                .annotation("annotation")
                .eventDate(LocalDateTime.now().plusDays(5).toString())
                .description("description")
                .createdOn(LocalDateTime.now().toString())
                .paid(true)
                .participantLimit(10)
                .requestModeration(false)
                .state(PENDING)
                .title("title")
                .build();

        JsonContent<EventDto> result = json.write(eventDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.annotation").isEqualTo("annotation");
        assertThat(result).extractingJsonPathStringValue("$.eventDate").isEqualTo(eventDto.getEventDate());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathStringValue("$.createdOn").isEqualTo(eventDto.getCreatedOn());
        assertThat(result).extractingJsonPathBooleanValue("$.paid").isTrue();
        assertThat(result).extractingJsonPathNumberValue("$.participantLimit").isEqualTo(10);
        assertThat(result).extractingJsonPathBooleanValue("$.requestModeration").isFalse();
        assertThat(result).extractingJsonPathStringValue("$.state").isEqualTo(PENDING.toString());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo("title");
    }
}
