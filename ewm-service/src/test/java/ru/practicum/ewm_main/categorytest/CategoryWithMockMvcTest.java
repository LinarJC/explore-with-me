package ru.practicum.ewm_main.categorytest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm_main.category.controller.AdminCategoryController;
import ru.practicum.ewm_main.category.controller.PublicCategoryController;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.service.CategoryService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AdminCategoryController.class, PublicCategoryController.class})
public class CategoryWithMockMvcTest {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mvc;

    private CategoryDto categoryDto;

    @BeforeEach
    void init() {
        categoryDto = CategoryDto
                .builder()
                .id(1L)
                .name("categoryName")
                .build();
    }

    @Test
    void updateCategoryTest() throws Exception {
        when(categoryService.updateCategory(any()))
                .thenReturn(categoryDto);
        mvc.perform(patch("/admin/categories")
                .content(mapper.writeValueAsString(categoryDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(categoryDto)));
    }

    @Test
    void createCategoryTest() throws Exception {
        when(categoryService.createCategory(any()))
                .thenReturn(categoryDto);
        mvc.perform(post("/admin/categories")
                        .content(mapper.writeValueAsString(NewCategoryDto.builder().name("categoryName").build()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(categoryDto)));
    }

    @Test
    void getCategoriesTest() throws Exception {
        when(categoryService.getCategories(anyInt(), anyInt()))
                .thenReturn(List.of(categoryDto));
        mvc.perform(get("/categories?from=0&size=10")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(categoryDto))));
    }

    @Test
    void getCategory() throws Exception {
        when(categoryService.getCategory(anyLong()))
                .thenReturn(categoryDto);
        mvc.perform(get("/categories/1")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(categoryDto)));
    }
}
