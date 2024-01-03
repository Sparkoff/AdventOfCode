package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day8")
class Day8Test {

    @Test
    void test_first_star() {
        Day8 day = new Day8(List.of("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"));

        assertEquals(138, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day8 day = new Day8(List.of("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"));

        assertEquals(66, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day8 day = new Day8();

        assertEquals(43996, day.firstStar());
        assertEquals(35189, day.secondStar());
    }
}
