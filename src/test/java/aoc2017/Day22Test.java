package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day22")
class Day22Test {

    @Test
    void test_first_star() {
        Day22 day = new Day22(List.of("..#",
                "#..",
                "..."));

        assertEquals(5587, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day22 day = new Day22(List.of("..#",
                "#..",
                "..."));

        assertEquals(2511944, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day22 day = new Day22();

        assertEquals(5369, day.firstStar());
        assertEquals(2510774, day.secondStar());
    }

}
