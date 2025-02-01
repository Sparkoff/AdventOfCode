package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day9")
class Day9Test {

    @Test
    void test_first_star() {
        Day9 day = new Day9(List.of("2333133121414131402"));

        assertEquals(1928L, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day9 day = new Day9(List.of("2333133121414131402"));

        assertEquals(2858L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day9 day = new Day9();

        assertEquals(6288707484810L, day.firstStar());
        assertEquals(6311837662089L, day.secondStar());
    }
}
