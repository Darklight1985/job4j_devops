package ru.job4j.devops.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.Type;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
public class CalcEventListenerTest {

    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    private static final KafkaContainer KAFKA = new KafkaContainer(
            DockerImageName.parse("apache/kafka:3.7.2")
    );

    private KafkaTemplate<String, Object> kafkaTemplate;

    private CalcEventRepository calcEventRepository;

    private static final Long EVENT_ID = 2L;
    private static final Long USER_ID = 1L;

    public CalcEventListenerTest(@Autowired KafkaTemplate<String, Object> kafkaTemplate,
                                 @Autowired CalcEventRepository calcEventRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.calcEventRepository = calcEventRepository;
    }

    @BeforeAll
    static void beforeAll() {
        POSTGRES.start();
        KAFKA.start();
    }

    @AfterAll
    static void afterAll() {
        POSTGRES.stop();
        KAFKA.stop();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void whenSignupNewMember() {
        var calcEvent = new CalcEvent();
        calcEvent.setFirstArg(1.0);
        calcEvent.setSecondArg(2.0);
        calcEvent.setType(Type.ADDITION);
        calcEvent.setResult(3.0);
        calcEvent.setUserId(USER_ID);
        kafkaTemplate.send("calc", calcEvent);
        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(10, SECONDS)
                .untilAsserted(() -> {
                    var optionalUser = calcEventRepository.findByUserId(USER_ID);
                    assertThat(optionalUser).isPresent();
                });
    }
}
