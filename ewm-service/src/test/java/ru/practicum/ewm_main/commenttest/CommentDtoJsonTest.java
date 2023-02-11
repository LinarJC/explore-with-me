package ru.practicum.ewm_main.commenttest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewm_main.comment.dto.CommentDto;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CommentDtoJsonTest {
    @Autowired
    JacksonTester<CommentDto> json;

    @Test
    void testCommentDto() throws IOException {
        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .text("text of comment")
                .createdOn(String.valueOf(LocalDateTime.of(2022, 12, 12, 10, 10, 1)))
                .authorName("name")
                .build();

        JsonContent<CommentDto> result = json.write(commentDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("text of comment");
        assertThat(result).extractingJsonPathStringValue("$.createdOn")
                .isEqualTo(LocalDateTime.of(2022, 12, 12, 10, 10, 1).toString());
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("name");
    }
}
