package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day4")
class Day4Test {

    @Test
    void test_first_star() {
        Day4 day = new Day4(List.of("MMMSXXMASM",
                "MSAMXMSMSA",
                "AMXSXMAAMM",
                "MSAMASMSMX",
                "XMASAMXAMM",
                "XXAMMXXAMA",
                "SMSMSASXSS",
                "SAXAMASAAA",
                "MAMMMXMMMM",
                "MXMXAXMASX"));

        assertEquals(18, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day4 day = new Day4(List.of("MMMSXXMASM",
                "MSAMXMSMSA",
                "AMXSXMAAMM",
                "MSAMASMSMX",
                "XMASAMXAMM",
                "XXAMMXXAMA",
                "SMSMSASXSS",
                "SAXAMASAAA",
                "MAMMMXMMMM",
                "MXMXAXMASX"));

        assertEquals(9, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day4 day = new Day4();

        assertEquals(2662, day.firstStar());
        assertEquals(2034, day.secondStar());
    }
}
