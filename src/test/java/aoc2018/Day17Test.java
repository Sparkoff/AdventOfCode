package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day17")
class Day17Test {

    @Test
    void test_first_star() {
        Day17 day = new Day17(List.of("x=495, y=2..7",
                "y=7, x=495..501",
                "x=501, y=3..7",
                "x=498, y=2..4",
                "x=506, y=1..2",
                "x=498, y=10..13",
                "x=504, y=10..13",
                "y=13, x=498..504"));

        assertEquals(57, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day17 day = new Day17(List.of("x=495, y=2..7",
                "y=7, x=495..501",
                "x=501, y=3..7",
                "x=498, y=2..4",
                "x=506, y=1..2",
                "x=498, y=10..13",
                "x=504, y=10..13",
                "y=13, x=498..504"));

        assertEquals(29, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day17 day = new Day17();

        assertEquals(31788, day.firstStar());
        assertEquals(25800, day.secondStar());
    }
}
