package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day3")
class Day3Test {

    @Test
    void test_first_star() {
        Day3 day = new Day3(List.of("00100", "11110", "10110", "10111",
                "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010"));

        assertEquals(198, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day3 day = new Day3(List.of("00100", "11110", "10110", "10111",
                "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010"));

        assertEquals(230, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day3 day = new Day3();

        assertEquals(3813416, day.firstStar());
        assertEquals(2990784, day.secondStar());
    }

}