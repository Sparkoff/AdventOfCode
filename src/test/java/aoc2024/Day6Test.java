package aoc2024;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day6")
class Day6Test {

    @Test
    void test_first_star() {
        Day6 day = new Day6(List.of("....#.....",
                ".........#",
                "..........",
                "..#.......",
                ".......#..",
                "..........",
                ".#..^.....",
                "........#.",
                "#.........",
                "......#..."));

        assertEquals(41, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day6 day = new Day6(List.of("....#.....",
                ".........#",
                "..........",
                "..#.......",
                ".......#..",
                "..........",
                ".#..^.....",
                "........#.",
                "#.........",
                "......#..."));

        assertEquals(6, day.secondStar());
    }

    @Test
    @Disabled("Test disabled due to long duration: 7sec")
    void test_real_inputs() {
        Day6 day = new Day6();

        assertEquals(4939, day.firstStar());
        assertEquals(1434, day.secondStar());
    }
}
