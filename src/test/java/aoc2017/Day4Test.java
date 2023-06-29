package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day4Test {

    @Test
    void test_first_star() {
        Day4 day = new Day4(List.of("aa bb cc dd ee",
                "aa bb cc dd aa",
                "aa bb cc dd aaa"));

        assertEquals(2, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day4 day = new Day4(List.of("abcde fghij",
                "abcde xyz ecdab",
                "a ab abc abd abf abj",
                "iiii oiii ooii oooi oooo",
                "oiii ioii iioi iiio"));

        assertEquals(3, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day4 day = new Day4();

        assertEquals(383, day.firstStar());
        assertEquals(265, day.secondStar());
    }

}
