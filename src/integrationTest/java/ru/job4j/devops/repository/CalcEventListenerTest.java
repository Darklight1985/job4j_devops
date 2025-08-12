package ru.job4j.devops.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import ru.job4j.devops.config.KafkaContainerConfig;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.Type;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
public class CalcEventListenerTest extends KafkaContainerConfig {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final CalcEventRepository calcEventRepository;

    private static final Long USER_ID = 1L;

    public CalcEventListenerTest(@Autowired KafkaTemplate<String, Object> kafkaTemplate,
                                 @Autowired CalcEventRepository calcEventRepository) {
        this.kafkaTemplate = new KafkaTemplate<>(kafkaTemplate.getProducerFactory());
        this.calcEventRepository = calcEventRepository;
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
