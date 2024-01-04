package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day1")
class Day1Test {

    @Test
    void test_first_star() {
        Day1 day = new Day1(List.of("1abc2",
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet"));

        assertEquals(142, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day1 day = new Day1(List.of("two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen"));

        assertEquals(281, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day1 day = new Day1();

        assertEquals(53651, day.firstStar());
        assertEquals(53894, day.secondStar());
    }
}
