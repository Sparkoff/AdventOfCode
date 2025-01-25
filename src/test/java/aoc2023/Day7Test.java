package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day7")
class Day7Test {

    @Test
    void test_first_star() {
        Day7 day = new Day7(List.of("32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483"));

        assertEquals(6440, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day7 day = new Day7(List.of("32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483"));

        assertEquals(5905, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day7 day = new Day7();

        assertEquals(253910319, day.firstStar());
        assertEquals(254083736, day.secondStar());
    }
}
