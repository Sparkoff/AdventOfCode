package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day14")
class Day14Test {

    @Test
    void test_first_star() {
        Day14 day = new Day14(List.of("O....#....",
                "O.OO#....#",
                ".....##...",
                "OO.#O....O",
                ".O.....O#.",
                "O.#..O.#.#",
                "..O..#O..O",
                ".......O..",
                "#....###..",
                "#OO..#...."));

        assertEquals(136, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day14 day = new Day14(List.of("O....#....",
                "O.OO#....#",
                ".....##...",
                "OO.#O....O",
                ".O.....O#.",
                "O.#..O.#.#",
                "..O..#O..O",
                ".......O..",
                "#....###..",
                "#OO..#...."));

        assertEquals(64, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day14 day = new Day14();

        assertEquals(108857, day.firstStar());
        assertEquals(95273, day.secondStar());
    }
}
