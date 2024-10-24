package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day13")
class Day13Test {

    @Test
    void test_first_star() {
        Day13 day = new Day13(List.of("#.##..##.",
                "..#.##.#.",
                "##......#",
                "##......#",
                "..#.##.#.",
                "..##..##.",
                "#.#.##.#.",
                "",
                "#...##..#",
                "#....#..#",
                "..##..###",
                "#####.##.",
                "#####.##.",
                "..##..###",
                "#....#..#"));

        assertEquals(405, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day13 day = new Day13(List.of("#.##..##.",
                "..#.##.#.",
                "##......#",
                "##......#",
                "..#.##.#.",
                "..##..##.",
                "#.#.##.#.",
                "",
                "#...##..#",
                "#....#..#",
                "..##..###",
                "#####.##.",
                "#####.##.",
                "..##..###",
                "#....#..#"));

        assertEquals(400, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day13 day = new Day13();

        assertEquals(30487, day.firstStar());
        assertEquals(31954, day.secondStar());
    }
}
