package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day8")
class Day8Test {

    @Test
    void test_first_star() {
        Day8 day = new Day8(List.of("............",
                "........0...",
                ".....0......",
                ".......0....",
                "....0.......",
                "......A.....",
                "............",
                "............",
                "........A...",
                ".........A..",
                "............",
                "............"));

        assertEquals(14, day.firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(9, new Day8(List.of("T.........",
                "...T......",
                ".T........",
                "..........",
                "..........",
                "..........",
                "..........",
                "..........",
                "..........",
                "..........")).secondStar());
        assertEquals(34, new Day8(List.of("............",
                "........0...",
                ".....0......",
                ".......0....",
                "....0.......",
                "......A.....",
                "............",
                "............",
                "........A...",
                ".........A..",
                "............",
                "............")).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day8 day = new Day8();

        assertEquals(361, day.firstStar());
        assertEquals(1249, day.secondStar());
    }
}
