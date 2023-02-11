package ru.practicum.ewm_main.compilationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm_main.compilation.controller.AdminControllerCompilation;
import ru.practicum.ewm_main.compilation.controller.PublicControllerCompilation;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.dto.ShortCompilationDto;
import ru.practicum.ewm_main.compilation.service.CompilationService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AdminControllerCompilation.class, PublicControllerCompilation.class})
public class CompilationWithMockMvcTest {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CompilationService compilationService;

    @Autowired
    private MockMvc mvc;

    private CompilationDto compilationDto;

    @BeforeEach
    void init() {
        compilationDto = CompilationDto.builder()
                .id(1L)
                .pinned(true)
                .title("title")
                .build();
    }

    @Test
    void createCompilationTest() throws Exception {
        when(compilationService.createCompilation(any()))
                .thenReturn(compilationDto);
        mvc.perform(post("/admin/compilations")
                .content(mapper.writeValueAsString(ShortCompilationDto.builder().title("title").pinned(true).build()))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(compilationDto)));
    }

    @Test
    void getCompilationsTest() throws Exception {
        when(compilationService.getCompilations(anyBoolean(), anyInt(), anyInt()))
                .thenReturn(List.of(compilationDto));
        mvc.perform(get("/compilations?pinned=true&from=0&size=10")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(compilationDto))));
    }

    @Test
    void getCompilationTest() throws Exception {
        when(compilationService.getCompilation(anyLong()))
                .thenReturn(compilationDto);
        mvc.perform(get("/compilations/1")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(compilationDto)));
    }
}
