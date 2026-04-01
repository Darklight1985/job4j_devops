package ru.job4j.devops.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;
import ru.job4j.devops.services.ResultService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("calc")
@AllArgsConstructor
public class CalcController {

    private final ResultService resultService;

    @PostMapping("summarise")
    public ResponseEntity<Result> summarise(@RequestBody TwoArgs twoArgs) {
        return ResponseEntity.ok(resultService.save(twoArgs));
    }

    @GetMapping("/")
    public ResponseEntity<List<Result>> logs() {
        return ResponseEntity.ok(resultService.findAll());
    }

    @PostMapping("times")
    public ResponseEntity<Result> times(@RequestBody TwoArgs twoArgs) {
        var result = new Result();
        result.setFirstArg(twoArgs.getFirst());
        result.setSecondArg(twoArgs.getSecond());
        result.setResult(twoArgs.getFirst() * twoArgs.getSecond());
        result.setOperation("+");
        result.setCreateDate(LocalDate.now());
        return ResponseEntity.ok(result);
    }
}