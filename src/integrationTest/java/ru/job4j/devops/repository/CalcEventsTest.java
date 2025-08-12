package ru.job4j.devops.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.services.CalcService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CalcEventsTest {
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    ).withReuse(true);

    private final CalcService calcService;

    private static final Long USER_ID = 1L;
    private static final User user = new User();

    CalcEventsTest(@Autowired CalcService calcService) {
        this.calcService = calcService;
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        user.setId(USER_ID);
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    public void whenSaveUser() {
        CalcEvent calcEvent = calcService.add(user, 1, 2);
        Assertions.assertEquals(calcEvent.getUserId(), USER_ID);
        Assertions.assertEquals(3.0, calcEvent.getResult());
    }
}
