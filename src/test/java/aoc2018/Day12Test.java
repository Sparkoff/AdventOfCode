package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day12")
class Day12Test {

    @Test
    void test_first_star() {
        Day12 day = new Day12(List.of("initial state: #..#.#..##......###...###",
                "",
                "...## => #",
                "..#.. => #",
                ".#... => #",
                ".#.#. => #",
                ".#.## => #",
                ".##.. => #",
                ".#### => #",
                "#.#.# => #",
                "#.### => #",
                "##.#. => #",
                "##.## => #",
                "###.. => #",
                "###.# => #",
                "####. => #"));

        assertEquals(325, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day12 day = new Day12();

        assertEquals(2444, day.firstStar());
        assertEquals(750000000697L, day.secondStar());
    }

}