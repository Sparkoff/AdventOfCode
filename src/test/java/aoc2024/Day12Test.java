package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day12")
class Day12Test {

    @Test
    void test_first_star() {
        assertEquals(140, new Day12(List.of("AAAA",
                "BBCD",
                "BBCC",
                "EEEC")).firstStar());
        assertEquals(772, new Day12(List.of("OOOOO",
                "OXOXO",
                "OOOOO",
                "OXOXO",
                "OOOOO")).firstStar());
        assertEquals(1930, new Day12(List.of("RRRRIICCFF",
                "RRRRIICCCF",
                "VVRRRCCFFF",
                "VVRCCCJFFF",
                "VVVVCJJCFE",
                "VVIVCCJJEE",
                "VVIIICJJEE",
                "MIIIIIJJEE",
                "MIIISIJEEE",
                "MMMISSJEEE")).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(80, new Day12(List.of("AAAA",
                "BBCD",
                "BBCC",
                "EEEC")).secondStar());
        assertEquals(436, new Day12(List.of("OOOOO",
                "OXOXO",
                "OOOOO",
                "OXOXO",
                "OOOOO")).secondStar());
        assertEquals(236, new Day12(List.of("EEEEE",
                "EXXXX",
                "EEEEE",
                "EXXXX",
                "EEEEE")).secondStar());
        assertEquals(368, new Day12(List.of("AAAAAA",
                "AAABBA",
                "AAABBA",
                "ABBAAA",
                "ABBAAA",
                "AAAAAA")).secondStar());
        assertEquals(1206, new Day12(List.of("RRRRIICCFF",
                "RRRRIICCCF",
                "VVRRRCCFFF",
                "VVRCCCJFFF",
                "VVVVCJJCFE",
                "VVIVCCJJEE",
                "VVIIICJJEE",
                "MIIIIIJJEE",
                "MIIISIJEEE",
                "MMMISSJEEE")).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day12 day = new Day12();

        assertEquals(1361494, day.firstStar());
        assertEquals(830516, day.secondStar());
    }
}
