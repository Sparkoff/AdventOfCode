package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day23Test {

    @Test
    void test_first_star() {
        Day23 day = new Day23(List.of("#############",
                "#...........#",
                "###B#C#B#D###",
                "  #A#D#C#A#",
                "  #########"));

        assertEquals(12521, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day23 day = new Day23(List.of("#############",
                "#...........#",
                "###B#C#B#D###",
                "  #A#D#C#A#",
                "  #########"));

        assertEquals(44169, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day23 day = new Day23();

        assertEquals(15338, day.firstStar());
        assertEquals(47064, day.secondStar());
    }

}