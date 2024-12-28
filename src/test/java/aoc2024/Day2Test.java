package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day2")
class Day2Test {

    @Test
    void test_first_star() {
        Day2 day = new Day2(List.of("7 6 4 2 1",
                "1 2 7 8 9",
                "9 7 6 2 1",
                "1 3 2 4 5",
                "8 6 4 4 1",
                "1 3 6 7 9"));

        assertEquals(2, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day2 day = new Day2(List.of("7 6 4 2 1",
                "1 2 7 8 9",
                "9 7 6 2 1",
                "1 3 2 4 5",
                "8 6 4 4 1",
                "1 3 6 7 9"));

        assertEquals(4, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day2 day = new Day2();

        assertEquals(369, day.firstStar());
        assertEquals(428, day.secondStar());
    }
}
