package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day15")
class Day15Test {

    @Test
    void test_first_star() {
        Day15 day = new Day15(List.of("1163751742",
                "1381373672",
                "2136511328",
                "3694931569",
                "7463417111",
                "1319128137",
                "1359912421",
                "3125421639",
                "1293138521",
                "2311944581"
        ));

        assertEquals(40, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day15 day = new Day15(List.of("1163751742",
                "1381373672",
                "2136511328",
                "3694931569",
                "7463417111",
                "1319128137",
                "1359912421",
                "3125421639",
                "1293138521",
                "2311944581"
        ));

        assertEquals(315, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day15 day = new Day15();

        assertEquals(602, day.firstStar());
        assertEquals(2935, day.secondStar());
    }
}
