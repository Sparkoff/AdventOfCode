package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day3Test {

    @Test
    void test_first_star() {
        assertEquals(0, (new Day3(List.of("1"))).firstStar());
        assertEquals(3, (new Day3(List.of("12"))).firstStar());
        assertEquals(2, (new Day3(List.of("23"))).firstStar());
        assertEquals(31, (new Day3(List.of("1024"))).firstStar());
    }

    @Test
    void test_second_star() {
        Day3 day = new Day3(List.of("800"));

        assertEquals(806, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day3 day = new Day3();

        assertEquals(371, day.firstStar());
        assertEquals(369601, day.secondStar());
    }

}
