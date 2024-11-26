package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day22")
class Day22Test {

    @Test
    void test_first_star() {
        Day22 day = new Day22(List.of("1,0,1~1,2,1",
                "0,0,2~2,0,2",
                "0,2,3~2,2,3",
                "0,0,4~0,2,4",
                "2,0,5~2,2,5",
                "0,1,6~2,1,6",
                "1,1,8~1,1,9"));

        assertEquals(5, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day22 day = new Day22(List.of("1,0,1~1,2,1",
                "0,0,2~2,0,2",
                "0,2,3~2,2,3",
                "0,0,4~0,2,4",
                "2,0,5~2,2,5",
                "0,1,6~2,1,6",
                "1,1,8~1,1,9"));

        assertEquals(7, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day22 day = new Day22();

        assertEquals(443, day.firstStar());
        assertEquals(69915, day.secondStar());
    }
}
