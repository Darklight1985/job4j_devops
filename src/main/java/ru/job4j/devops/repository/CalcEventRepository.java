package ru.job4j.devops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.devops.models.CalcEvent;

@Repository
public interface CalcEventRepository extends CrudRepository<CalcEvent, Long> {

}