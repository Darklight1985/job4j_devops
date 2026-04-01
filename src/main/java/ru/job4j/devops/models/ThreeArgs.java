package ru.job4j.devops.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.devops.services.CalculatorService;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreeArgs {
    private double first;
    private double second;
    private CalculatorService.Operation operation;
}
