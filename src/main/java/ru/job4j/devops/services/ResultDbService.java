package ru.job4j.devops.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;
import ru.job4j.devops.repositories.ResultRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultDbService implements ResultService {

    private final ResultRepository resultRepository;

    @Override
    public Result save(TwoArgs twoArgs) {
        var result = new Result();
        result.setFirstArg(twoArgs.getFirst());
        result.setSecondArg(twoArgs.getSecond());
        result.setResult(twoArgs.getFirst() + twoArgs.getSecond());
        result.setOperation("+");
        result.setCreateDate(LocalDate.now());
        return resultRepository.save(result);
    }

    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }
}
