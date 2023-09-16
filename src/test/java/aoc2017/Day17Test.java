package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day17Test {

    @Test
    void test_first_star() {
        Day17 day = new Day17(List.of("3"));

        assertEquals(638, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day17 day = new Day17(List.of("3"));

        assertEquals(1222153, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day17 day = new Day17();

        assertEquals(808, day.firstStar());
        assertEquals(47465686, day.secondStar());
    }

}
