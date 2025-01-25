package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day24")
class Day24Test {

    @Test
    void test_first_star() {
        Day24 day = new Day24(List.of("19, 13, 30 @ -2, 1, -2",
                "18, 19, 22 @ -1, -1, -2",
                "20, 25, 34 @ -2, -2, -4",
                "12, 31, 28 @ -1, -2, -1",
                "20, 19, 15 @ 1, -5, -3"), 7L, 27L);

        assertEquals(2, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day24 day = new Day24(List.of("19, 13, 30 @ -2, 1, -2",
                "18, 19, 22 @ -1, -1, -2",
                "20, 25, 34 @ -2, -2, -4",
                "12, 31, 28 @ -1, -2, -1",
                "20, 19, 15 @ 1, -5, -3"));

        assertEquals(47L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day24 day = new Day24();

        assertEquals(24627, day.firstStar());
        assertEquals(527310134398221L, day.secondStar());
    }
}
