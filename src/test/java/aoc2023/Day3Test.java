package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day3")
class Day3Test {

    @Test
    void test_first_star() {
        Day3 day = new Day3(List.of("467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."));

        assertEquals(4361, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day3 day = new Day3(List.of("467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."));

        assertEquals(467835, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day3 day = new Day3();

        assertEquals(544433, day.firstStar());
        assertEquals(76314915, day.secondStar());
    }
}
