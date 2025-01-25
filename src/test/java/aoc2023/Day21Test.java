package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day21")
class Day21Test {

    @Test
    void test_first_star() {
        Day21 day = new Day21(List.of("...........",
                ".....###.#.",
                ".###.##..#.",
                "..#.#...#..",
                "....#.#....",
                ".##..S####.",
                ".##..#...#.",
                ".......##..",
                ".##.#.####.",
                ".##..##.##.",
                "..........."), 6);

        assertEquals(16, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day21 day = new Day21();

        assertEquals(3724, day.firstStar());
        assertEquals(620348631910321L, day.secondStar());
    }
}
