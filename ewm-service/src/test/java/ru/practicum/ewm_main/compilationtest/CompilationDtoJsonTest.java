package ru.practicum.ewm_main.compilationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CompilationDtoJsonTest {
    @Autowired
    private JacksonTester<CompilationDto> json;

    @Test
    void testCompilationDto() throws Exception {
        CompilationDto compilationDto = CompilationDto.builder()
                .id(1L)
                .title("title")
                .pinned(true)
                .build();

        JsonContent<CompilationDto> result = json.write(compilationDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo("title");
        assertThat(result).extractingJsonPathBooleanValue("$.pinned").isTrue();
    }
}
