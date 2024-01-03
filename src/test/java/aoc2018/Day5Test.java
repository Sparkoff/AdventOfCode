package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day5")
class Day5Test {

    @Test
    void test_first_star() {
        assertEquals(0, new Day5(List.of("aA")).firstStar());
        assertEquals(0, new Day5(List.of("abBA")).firstStar());
        assertEquals(4, new Day5(List.of("abAB")).firstStar());
        assertEquals(6, new Day5(List.of("aabAAB")).firstStar());
        assertEquals(10, new Day5(List.of("dabAcCaCBAcCcaDA")).firstStar());
    }

    @Test
    void test_second_star() {
        Day5 day = new Day5(List.of("dabAcCaCBAcCcaDA"));

        assertEquals(4, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day5 day = new Day5();

        assertEquals(10766, day.firstStar());
        assertEquals(6538, day.secondStar());
    }
}
