package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day9")
class Day9Test {

    @Test
    void test_first_star() {
        Day9 day = new Day9(List.of("0 3 6 9 12 15",
                "1 3 6 10 15 21",
                "10 13 16 21 30 45"));

        assertEquals(114, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day9 day = new Day9(List.of("0 3 6 9 12 15",
                "1 3 6 10 15 21",
                "10 13 16 21 30 45"));

        assertEquals(2, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day9 day = new Day9();

        assertEquals(1901217887, day.firstStar());
        assertEquals(905, day.secondStar());
    }
}
