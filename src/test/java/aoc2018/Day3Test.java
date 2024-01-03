package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day3")
class Day3Test {

    @Test
    void test_first_star() {
        Day3 day = new Day3(List.of("#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2"));

        assertEquals(4, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day3 day = new Day3(List.of("#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2"));

        assertEquals(3, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day3 day = new Day3();

        assertEquals(112378, day.firstStar());
        assertEquals(603, day.secondStar());
    }
}
