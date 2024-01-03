package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day6")
class Day6Test {

    @Test
    void test_first_star() {
        Day6 day = new Day6(List.of("0 2 7 0"));

        assertEquals(5, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day6 day = new Day6(List.of("0 2 7 0"));

        assertEquals(4, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day6 day = new Day6();

        assertEquals(7864, day.firstStar());
        assertEquals(1695, day.secondStar());
    }
}
