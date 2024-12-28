package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day1")
class Day1Test {

    @Test
    void test_first_star() {
        Day1 day = new Day1(List.of("3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3"));

        assertEquals(11, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day1 day = new Day1(List.of("3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3"));

        assertEquals(31, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day1 day = new Day1();

        assertEquals(2904518, day.firstStar());
        assertEquals(18650129, day.secondStar());
    }
}
