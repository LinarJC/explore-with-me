package ru.practicum.ewm_main.categorytest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.Constant;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.category.dto.NewCategoryDto;
import ru.practicum.ewm_main.category.service.CategoryService;
import ru.practicum.ewm_main.event.dto.LocationDto;
import ru.practicum.ewm_main.event.dto.NewEventDto;
import ru.practicum.ewm_main.event.service.EventService;
import ru.practicum.ewm_main.exception.BadRequestException;
import ru.practicum.ewm_main.exception.NotFoundException;
import ru.practicum.ewm_main.user.dto.UserDto;
import ru.practicum.ewm_main.user.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    private CategoryDto categoryDto;

    private NewCategoryDto newCategoryDto;

    private NewEventDto newEventDto;

    @BeforeEach
    void init() {
        categoryDto = CategoryDto.builder().id(1L).name("newName").build();
        newCategoryDto = NewCategoryDto.builder().name("name").build();
        newEventDto = NewEventDto.builder()
                .annotation("annotation")
                .eventDate(LocalDateTime.now().plusDays(5).format(Constant.DATE_TIME_FORMATTER))
                .paid(true)
                .title("title")
                .description("description")
                .participantLimit(10)
                .requestModeration(false)
                .build();
    }

    @Test
    void getCategoriesTest() {
        categoryService.createCategory(newCategoryDto);
        assertEquals(1, categoryService.getCategories(0, 10).size());
    }

    @Test
    void getCategoryTest() {
        CategoryDto categoryDto1 = categoryService.createCategory(newCategoryDto);
        assertEquals("name", categoryService.getCategory(categoryDto1.getId()).getName());
    }

    @Test
    void getCategoryWithWrongIdTest() {
        assertThrows(NotFoundException.class, () -> categoryService.getCategory(99L));
    }

    @Test
    void updateCategoryTest() {
        categoryService.createCategory(newCategoryDto);
        CategoryDto updateCat = categoryService.updateCategory(categoryDto);
        assertEquals("newName", updateCat.getName());
    }

    @Test
    void deleteCategoryTest() {
        CategoryDto newCat = categoryService.createCategory(newCategoryDto);
        assertEquals(1, categoryService.getCategories(0, 10).size());
        categoryService.deleteCategory(newCat.getId());
        assertEquals(0, categoryService.getCategories(0, 10).size());
    }

    @Test
    void deleteCategoryWithEventsTest() {
        userService.createUser(UserDto.builder().email("email").name("name").build());
        CategoryDto categoryDto1 = categoryService.createCategory(newCategoryDto);
        newEventDto.setCategory(categoryDto1.getId());
        newEventDto.setLocation(LocationDto.builder().lat(20.20f).lon(10.10f).build());
        eventService.createEvent(1L, newEventDto);
        assertThrows(BadRequestException.class, () -> categoryService.deleteCategory(categoryDto1.getId()));
    }
}
