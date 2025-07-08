package ru.job4j.devops.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import ru.job4j.devops.models.TwoArgs;
import ru.job4j.devops.services.ResultFakeService;

import static org.assertj.core.api.Assertions.assertThat;

class CalcControllerTest {

    private CalcController calcController;
    private final ResultFakeService resultFakeService = new ResultFakeService();

    @BeforeEach
    void setUp() {
        calcController = new CalcController(resultFakeService);
    }

    @Test
    public void whenOnePlusOneThenTwo() {
        var input = new TwoArgs(1, 1);
        var expected = 2;
        var output = calcController.summarise(input);
        assertThat(output.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(output.getBody().getResult()).isEqualTo(expected);
    }

    @Test
    public void whenNegativeNumber() {
        var input = new TwoArgs(-1, -1);
        var expected = -2;
        var output = calcController.summarise(input);
        assertThat(output.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(output.getBody().getResult()).isEqualTo(expected);
    }

    @Test
    public void whenNegativePlusZero() {
        var input = new TwoArgs(-3, 0);
        var expected = -3;
        var output = calcController.summarise(input);
        assertThat(output.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(output.getBody().getResult()).isEqualTo(expected);
    }

    @Test
    public void whenZeroPlusZero() {
        var input = new TwoArgs(0, 3);
        var expected = 3;
        var output = calcController.summarise(input);
        assertThat(output.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(output.getBody().getResult()).isEqualTo(expected);
    }

    @Test
    public void whenTwoTimesTwoThenFour() {
        var input = new TwoArgs(2, 2);
        var expected = 4;
        var output = calcController.times(input);
        assertThat(output.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(output.getBody().getResult()).isEqualTo(expected);
    }

    @Test
    public void whenZeroTimesZero() {
        var input = new TwoArgs(0, 0);
        var expected = 0;
        var output = calcController.times(input);
        assertThat(output.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(output.getBody().getResult()).isEqualTo(expected);
    }

    @Test
    public void whenTimesNegatives() {
        var input = new TwoArgs(-3, -3);
        var expected = 9;
        var output = calcController.times(input);
        assertThat(output.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(output.getBody().getResult()).isEqualTo(expected);
    }

    @Test
    public void whenTwoTimesThreeThenSix() {
        var input = new TwoArgs(3, 4);
        var expected = 12;
        var output = calcController.times(input);
        assertThat(output.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(output.getBody().getResult()).isEqualTo(expected);
    }
}