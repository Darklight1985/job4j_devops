package ru.job4j.devops.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.devops.config.PostgresContainerConfig;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.services.CalcService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CalcEventsTest extends PostgresContainerConfig {

    private final CalcService calcService;

    private static final Long USER_ID = 1L;

    private static final User USER = new User();

    CalcEventsTest(@Autowired CalcService calcService) {
        this.calcService = calcService;
    }

    @Test
    public void whenSaveUser() {
        USER.setId(USER_ID);
        CalcEvent calcEvent = calcService.add(USER, 1, 2);
        Assertions.assertEquals(calcEvent.getUserId(), USER_ID);
        Assertions.assertEquals(3.0, calcEvent.getResult());
    }
}
