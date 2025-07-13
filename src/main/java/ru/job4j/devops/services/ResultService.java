package ru.job4j.devops.services;

import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;

import java.util.List;

public interface ResultService {

    Result save(TwoArgs twoArgs);

    List<Result> findAll();
}
