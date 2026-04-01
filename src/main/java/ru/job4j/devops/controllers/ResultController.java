package ru.job4j.devops.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.devops.models.ThreeArgs;
import ru.job4j.devops.services.CalculatorService;

@RestController
@RequestMapping("res")
@RequiredArgsConstructor
public class ResultController {

    private final CalculatorService calcService;

    @PostMapping("operations")
    public double times(@RequestBody ThreeArgs threeArgs) {
        return calcService.add(threeArgs.getFirst(), threeArgs.getSecond(), threeArgs.getOperation());
    }
}
