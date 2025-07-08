package ru.job4j.devops.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.devops.models.Result;

import java.util.List;

public interface ResultRepository extends CrudRepository<Result, Long> {
    List<Result> findAll();
}
