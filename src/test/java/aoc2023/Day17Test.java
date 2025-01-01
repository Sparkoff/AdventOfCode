package aoc2023;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day17")
class Day17Test {

    @Test
    void test_first_star() {
        Day17 day = new Day17(List.of("2413432311323",
                "3215453535623",
                "3255245654254",
                "3446585845452",
                "4546657867536",
                "1438598798454",
                "4457876987766",
                "3637877979653",
                "4654967986887",
                "4564679986453",
                "1224686865563",
                "2546548887735",
                "4322674655533"));

        assertEquals(102, day.firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(94, new Day17(List.of("2413432311323",
                "3215453535623",
                "3255245654254",
                "3446585845452",
                "4546657867536",
                "1438598798454",
                "4457876987766",
                "3637877979653",
                "4654967986887",
                "4564679986453",
                "1224686865563",
                "2546548887735",
                "4322674655533")).secondStar());
        assertEquals(71, new Day17(List.of("111111111111",
                "999999999991",
                "999999999991",
                "999999999991",
                "999999999991")).secondStar());
    }

    @Test
    @Disabled("Test disabled due to long duration: 7min 19sec")
    void test_real_inputs() {
        Day17 day = new Day17();

        assertEquals(1244, day.firstStar());
        assertEquals(1367, day.secondStar());
    }
}
