package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day15")
class Day15Test {

    @Test
    void test_first_star() {
        assertEquals(27730, new Day15(List.of("#######",
                "#.G...#",
                "#...EG#",
                "#.#.#G#",
                "#..G#E#",
                "#.....#",
                "#######")).firstStar());
        assertEquals(36334, new Day15(List.of("#######",
                "#G..#E#",
                "#E#E.E#",
                "#G.##.#",
                "#...#E#",
                "#...E.#",
                "#######")).firstStar());
        assertEquals(39514, new Day15(List.of("#######",
                "#E..EG#",
                "#.#G.E#",
                "#E.##E#",
                "#G..#.#",
                "#..E#.#",
                "#######")).firstStar());
        assertEquals(27755, new Day15(List.of("#######",
                "#E.G#.#",
                "#.#G..#",
                "#G.#.G#",
                "#G..#.#",
                "#...E.#",
                "#######")).firstStar());
        assertEquals(28944, new Day15(List.of("#######",
                "#.E...#",
                "#.#..G#",
                "#.###.#",
                "#E#G#G#",
                "#...#G#",
                "#######")).firstStar());
        assertEquals(18740, new Day15(List.of("#########",
                "#G......#",
                "#.E.#...#",
                "#..##..G#",
                "#...##..#",
                "#...#...#",
                "#.G...G.#",
                "#.....G.#",
                "#########")).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(4988, new Day15(List.of("#######",
                "#.G...#",
                "#...EG#",
                "#.#.#G#",
                "#..G#E#",
                "#.....#",
                "#######")).secondStar());
        assertEquals(31284, new Day15(List.of("#######",
                "#E..EG#",
                "#.#G.E#",
                "#E.##E#",
                "#G..#.#",
                "#..E#.#",
                "#######")).secondStar());
        assertEquals(3478, new Day15(List.of("#######",
                "#E.G#.#",
                "#.#G..#",
                "#G.#.G#",
                "#G..#.#",
                "#...E.#",
                "#######")).secondStar());
        assertEquals(6474, new Day15(List.of("#######",
                "#.E...#",
                "#.#..G#",
                "#.###.#",
                "#E#G#G#",
                "#...#G#",
                "#######")).secondStar());
        assertEquals(1140, new Day15(List.of("#########",
                "#G......#",
                "#.E.#...#",
                "#..##..G#",
                "#...##..#",
                "#...#...#",
                "#.G...G.#",
                "#.....G.#",
                "#########")).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day15 day = new Day15();


        assertEquals(248235, day.firstStar());
        assertEquals(46784, day.secondStar());
    }

}
