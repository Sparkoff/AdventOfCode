package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day17")
class Day17Test {

    @Test
    void test_first_star() {
        Day17 day = new Day17(List.of("target area: x=20..30, y=-10..-5"));

        assertEquals(45, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day17 day = new Day17(List.of("target area: x=20..30, y=-10..-5"));

        assertEquals(112, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day17 day = new Day17();

        assertEquals(3160, day.firstStar());
        assertEquals(1928, day.secondStar());
    }
}
