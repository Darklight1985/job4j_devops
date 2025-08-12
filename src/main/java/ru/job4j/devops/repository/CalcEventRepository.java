package ru.job4j.devops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.devops.models.CalcEvent;

import java.util.Optional;

@Repository
public interface CalcEventRepository extends CrudRepository<CalcEvent, Long> {

    Optional<CalcEvent> findByUserId(Long id);

}