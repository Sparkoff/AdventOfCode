package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day6")
class Day6Test {

    @Test
    void test_first_star() {
        Day6 day = new Day6(List.of("Time:      7  15   30",
                "Distance:  9  40  200"));

        assertEquals(288, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day6 day = new Day6(List.of("Time:      7  15   30",
                "Distance:  9  40  200"));

        assertEquals(71503, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day6 day = new Day6();

        assertEquals(1312850, day.firstStar());
        assertEquals(36749103, day.secondStar());
    }
}
