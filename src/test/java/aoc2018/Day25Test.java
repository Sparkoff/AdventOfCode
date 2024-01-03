package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day25")
class Day25Test {

    @Test
    void test_first_star() {
        Day25 day = new Day25(List.of("0,0,0,0",
                "3,0,0,0",
                "0,3,0,0",
                "0,0,3,0",
                "0,0,0,3",
                "0,0,0,6",
                "9,0,0,0",
                "12,0,0,0"));

        assertEquals(2, day.firstStar());

        day = new Day25(List.of("-1,2,2,0",
                "0,0,2,-2",
                "0,0,0,-2",
                "-1,2,0,0",
                "-2,-2,-2,2",
                "3,0,2,-1",
                "-1,3,2,2",
                "-1,0,-1,0",
                "0,2,1,-2",
                "3,0,0,0"));

        assertEquals(4, day.firstStar());

        day = new Day25(List.of("1,-1,0,1",
                "2,0,-1,0",
                "3,2,-1,0",
                "0,0,3,1",
                "0,0,-1,-1",
                "2,3,-2,0",
                "-2,2,0,0",
                "2,-2,0,-1",
                "1,-1,0,-1",
                "3,2,0,2"));

        assertEquals(3, day.firstStar());

        day = new Day25(List.of("1,-1,-1,-2",
                "-2,-2,0,1",
                "0,2,1,3",
                "-2,3,-2,1",
                "0,2,3,-2",
                "-1,-1,1,-2",
                "0,-2,-1,0",
                "-2,2,3,-1",
                "1,2,2,0",
                "-1,-2,0,-2"));

        assertEquals(8, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day25 day = new Day25();

        assertEquals(375, day.firstStar());
        assertEquals(2018, day.secondStar());
    }
}
