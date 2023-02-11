package ru.practicum.ewm_main.categorytest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewm_main.category.dto.CategoryDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CategoryDtoJsonTest {
    @Autowired
    JacksonTester<CategoryDto> json;

    @Test
    void testCategoryDto() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("categoryName")
                .build();

        JsonContent<CategoryDto> result = json.write(categoryDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("categoryName");
    }
}
