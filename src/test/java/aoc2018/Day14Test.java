package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day14")
class Day14Test {

    @Test
    void test_first_star() {
        assertEquals("5158916779", new Day14(List.of("9")).firstStar());
        assertEquals("0124515891", new Day14(List.of("5")).firstStar());
        assertEquals("9251071085", new Day14(List.of("18")).firstStar());
        assertEquals("5941429882", new Day14(List.of("2018")).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(9, new Day14(List.of("51589")).secondStar());
        assertEquals(5, new Day14(List.of("01245")).secondStar());
        assertEquals(18, new Day14(List.of("92510")).secondStar());
        assertEquals(2018, new Day14(List.of("59414")).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day14 day = new Day14();

        assertEquals("1631191756", day.firstStar());
        assertEquals(20219475, day.secondStar());
    }
}
