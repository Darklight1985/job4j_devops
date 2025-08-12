package ru.job4j.devops.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.Type;
import ru.job4j.devops.models.User;
import ru.job4j.devops.repository.CalcEventRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CalcService {

    private final CalcEventRepository calcEventRepository;

    public CalcEvent add(User user, int first, int second) {
        var calcEvent = new CalcEvent();
        calcEvent.setFirstArg((double) first);
        calcEvent.setSecondArg((double) second);
        calcEvent.setResult((double) (first + second));
        calcEvent.setType(Type.ADDITION);
        calcEvent.setCreateDate(LocalDate.now());
        calcEvent.setUserId(user.getId());
        return calcEventRepository.save(calcEvent);
    }

}
