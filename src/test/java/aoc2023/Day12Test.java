package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day12")
class Day12Test {

    @Test
    void test_first_star() {
        Day12 day = new Day12(List.of("???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1"));

        assertEquals(21, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day12 day = new Day12(List.of("???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1"));

        assertEquals(525152L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day12 day = new Day12();

        assertEquals(7402, day.firstStar());
        assertEquals(3384337640277L, day.secondStar());
    }
}
