package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day12")
class Day12Test {

    @Test
    void test_first_star() {
        Day12 day = new Day12(List.of("0 <-> 2",
                "1 <-> 1",
                "2 <-> 0, 3, 4",
                "3 <-> 2, 4",
                "4 <-> 2, 3, 6",
                "5 <-> 6",
                "6 <-> 4, 5"));

        assertEquals(6, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day12 day = new Day12(List.of("0 <-> 2",
                "1 <-> 1",
                "2 <-> 0, 3, 4",
                "3 <-> 2, 4",
                "4 <-> 2, 3, 6",
                "5 <-> 6",
                "6 <-> 4, 5"));

        assertEquals(2, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day12 day = new Day12();

        assertEquals(175, day.firstStar());
        assertEquals(213, day.secondStar());
    }

}
