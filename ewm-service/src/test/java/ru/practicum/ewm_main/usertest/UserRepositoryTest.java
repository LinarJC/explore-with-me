package ru.practicum.ewm_main.usertest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByIdInTest() {
        userRepository.save(User.builder().name("name").email("email@email.com").build());
        Page<User> users = userRepository.findAllByIdIn(List.of(1L), Pageable.ofSize(10));
        assertThat(users.stream().count(), equalTo(1L));
    }
}
