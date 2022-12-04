package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day6Test {

    @Test
    void test_first_star() {
        Day6 day = new Day6(List.of("3,4,3,1,2"));

        assertEquals(5934, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day6 day = new Day6(List.of("3,4,3,1,2"));

        assertEquals(26984457539L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day6 day = new Day6();

        assertEquals(352195, day.firstStar());
        assertEquals(1600306001288L, day.secondStar());
    }

}