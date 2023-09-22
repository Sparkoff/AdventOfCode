package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day13Test {

    @Test
    void test_first_star() {
        Day13 day = new Day13(List.of("0: 3",
                "1: 2",
                "4: 4",
                "6: 4"));

        assertEquals(24, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day13 day = new Day13(List.of("0: 3",
                "1: 2",
                "4: 4",
                "6: 4"));

        assertEquals(10, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day13 day = new Day13();

        assertEquals(3184, day.firstStar());
        assertEquals(3878062, day.secondStar());
    }

}
