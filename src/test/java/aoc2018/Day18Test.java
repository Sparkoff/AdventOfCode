package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day18")
class Day18Test {

    @Test
    void test_first_star() {
        Day18 day = new Day18(List.of(".#.#...|#.",
                ".....#|##|",
                ".|..|...#.",
                "..|#.....#",
                "#.#|||#|#|",
                "...#.||...",
                ".|....|...",
                "||...#|.#|",
                "|.||||..|.",
                "...#.|..|."));

        assertEquals(1147, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day18 day = new Day18();

        assertEquals(614812, day.firstStar());
        assertEquals(212176, day.secondStar());
    }
}
