package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day21Test {

    @Test
    void test_first_star() {
        Day21 day = new Day21(List.of("../.# => ##./#../...",
                ".#./..#/### => #..#/..../..../#..#"));

        assertEquals(12, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day21 day = new Day21();

        assertEquals(152, day.firstStar());
        assertEquals(1956174, day.secondStar());
    }

}
