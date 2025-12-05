package aoc2025;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2025 Day4")
class Day4Test {

    @Test
    void test_first_star() {
        Day4 day = new Day4(List.of("..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@."));

        assertEquals(13, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day4 day = new Day4(List.of("..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@."));

        assertEquals(43, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day4 day = new Day4();

        assertEquals(1389, day.firstStar());
        assertEquals(9000, day.secondStar());
    }
}
