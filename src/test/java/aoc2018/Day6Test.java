package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day6")
class Day6Test {

    @Test
    void test_first_star() {
        Day6 day = new Day6(List.of("1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9"));

        assertEquals(17, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day6 day = new Day6(List.of("1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9"));

        assertEquals(16, day.testMode().secondStar());
    }

    @Test
    void test_real_inputs() {
        Day6 day = new Day6();

        assertEquals(4290, day.firstStar());
        assertEquals(37318, day.secondStar());
    }
}
