package ru.practicum.ewm_main.compilationtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.Constant;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.service.CategoryService;
import ru.practicum.ewm_main.compilation.dto.CompilationDto;
import ru.practicum.ewm_main.compilation.dto.ShortCompilationDto;
import ru.practicum.ewm_main.compilation.service.CompilationService;
import ru.practicum.ewm_main.event.dto.EventDto;
import ru.practicum.ewm_main.event.dto.LocationDto;
import ru.practicum.ewm_main.event.dto.NewEventDto;
import ru.practicum.ewm_main.event.service.EventService;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompilationServiceTest {
    @Autowired
    private CompilationService compilationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private ShortCompilationDto shortCompilationDto;

    private NewEventDto newEventDto;

    private NewCategoryDto newCategoryDto;

    private UserDto userDto;

    private CompilationDto compilationDto;

    private UserDto userDto1;

    @BeforeEach
    void init() {
        shortCompilationDto = ShortCompilationDto.builder().title("title").pinned(true).build();
        newEventDto = NewEventDto.builder()
                .annotation("annotation")
                .eventDate(LocalDateTime.now().plusDays(5).format(Constant.DATE_TIME_FORMATTER))
                .paid(true)
                .title("title")
                .description("description")
                .participantLimit(10)
                .requestModeration(false)
                .location(LocationDto.builder().lon(20.20f).lat(10.10f).build())
                .build();
        newCategoryDto = NewCategoryDto.builder().name("name").build();
        userDto = UserDto.builder().name("name").email("email").build();
        userDto1 = userService.createUser(userDto);
        CategoryDto categoryDto = categoryService.createCategory(newCategoryDto);
        newEventDto.setCategory(categoryDto.getId());
        EventDto eventDto = eventService.createEvent(userDto1.getId(), newEventDto);
        shortCompilationDto.setEvents(List.of(eventDto.getId()));
        compilationDto = compilationService.createCompilation(shortCompilationDto);
    }

    @Test
    void getCompilationsTest() {
        assertEquals(1, compilationService.getCompilations(null, 0, 10).size());
        assertEquals(0, compilationService.getCompilations(false, 0, 10).size());
    }

    @Test
    void getCompilationTest() {
        assertEquals("title", compilationService.getCompilation(compilationDto.getId()).getTitle());
    }

    @Test
    void deleteCompilationTest() {
        assertNotNull(compilationService.getCompilation(compilationDto.getId()));
        compilationService.deleteCompilation(compilationDto.getId());
        assertEquals(0, compilationService.getCompilations(null, 0, 10).size());
    }

    @Test
    void deleteEventFromCompilationTest() {
        assertEquals(1, compilationService.getCompilation(compilationDto.getId()).getEvents().size());
        compilationService.deleteEventFromCompilation(compilationDto.getId(), compilationDto.getEvents().get(0).getId());
        assertEquals(0, compilationService.getCompilation(compilationDto.getId()).getEvents().size());
    }

    @Test
    void addEventToCompilationTest() {
        assertEquals(1, compilationService.getCompilation(compilationDto.getId()).getEvents().size());
        EventDto eventDto = eventService.createEvent(userDto1.getId(), newEventDto);
        compilationService.addEventToCompilation(compilationDto.getId(), eventDto.getId());
        assertEquals(2, compilationService.getCompilation(compilationDto.getId()).getEvents().size());
    }

    @Test
    void addAndDeleteCompilationFromMainPageTest() {
        assertEquals(true, compilationService.getCompilation(compilationDto.getId()).getPinned());
        compilationService.deleteCompilationFromMainPage(compilationDto.getId());
        assertEquals(false, compilationService.getCompilation(compilationDto.getId()).getPinned());
        compilationService.addCompilationToMainPage(compilationDto.getId());
        assertEquals(true, compilationService.getCompilation(compilationDto.getId()).getPinned());
    }
}
