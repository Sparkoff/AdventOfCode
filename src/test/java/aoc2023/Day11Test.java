package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day11")
class Day11Test {

    @Test
    void test_first_star() {
        Day11 day = new Day11(List.of("...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#....."));

        assertEquals(374, day.firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(1030L, new Day11(List.of("...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#....."), 10).secondStar());
        assertEquals(8410L, new Day11(List.of("...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#....."), 100).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day11 day = new Day11();

        assertEquals(9627977, day.firstStar());
        assertEquals(644248339497L, day.secondStar());
    }
}
