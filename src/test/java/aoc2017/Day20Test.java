package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day20")
class Day20Test {

    @Test
    void test_first_star() {
        Day20 day = new Day20(List.of("p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>",
                "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"));

        assertEquals(0, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day20 day = new Day20(List.of("p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>",
                "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>",
                "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>",
                "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>"));

        assertEquals(1, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day20 day = new Day20();

        assertEquals(125, day.firstStar());
        assertEquals(461, day.secondStar());
    }
}
