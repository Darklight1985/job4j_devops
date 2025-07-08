package ru.job4j.devops.services;

import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultFakeService implements ResultService {
    private final Map<Long, Result> mem = new HashMap<>();
    private long genId = 0;

    @Override
    public Result save(TwoArgs twoArgs) {
        var result = new Result();
        result.setFirstArg(twoArgs.getFirst());
        result.setSecondArg(twoArgs.getSecond());
        result.setResult(twoArgs.getFirst() + twoArgs.getSecond());
        result.setOperation("+");
        result.setCreateDate(LocalDate.now());
        result.setId(genId++);
        mem.put(genId, result);
        return result;
    }

    @Override
    public List<Result> findAll() {
        return new ArrayList<>(mem.values());
    }
}
