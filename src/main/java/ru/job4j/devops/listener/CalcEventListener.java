package ru.job4j.devops.listener;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.repository.CalcEventRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class CalcEventListener {

    private final CalcEventRepository calcEventRepository;

    @KafkaListener(topics = "calc", groupId = "job4j")
    public void calculate(CalcEvent calcEvent) {
        log.debug("calculation is ov: {}", calcEvent.getId());
        calcEvent = calcEventRepository.save(calcEvent);
        System.out.println(calcEvent.getId());
    }
}
