package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day14")
class Day14Test {

    @Test
    void test_first_star() {
        Day14 day = new Day14(List.of("p=0,4 v=3,-3",
                "p=6,3 v=-1,-3",
                "p=10,3 v=-1,2",
                "p=2,0 v=2,-1",
                "p=0,0 v=1,3",
                "p=3,0 v=-2,-2",
                "p=7,6 v=-1,-3",
                "p=3,0 v=-1,-2",
                "p=9,3 v=2,3",
                "p=7,3 v=-1,2",
                "p=2,4 v=2,-3",
                "p=9,5 v=-3,-3"), 11, 7);

        assertEquals(12, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day14 day = new Day14();

        assertEquals(225810288, day.firstStar());
        assertEquals(6752, day.secondStar());
    }
}
