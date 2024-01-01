package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day23")
class Day23Test {

    @Test
    void test_first_star() {
        Day23 day = new Day23(List.of("pos=<0,0,0>, r=4",
                "pos=<1,0,0>, r=1",
                "pos=<4,0,0>, r=3",
                "pos=<0,2,0>, r=1",
                "pos=<0,5,0>, r=3",
                "pos=<0,0,3>, r=1",
                "pos=<1,1,1>, r=1",
                "pos=<1,1,2>, r=1",
                "pos=<1,3,1>, r=1"));

        assertEquals(7, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day23 day = new Day23(List.of("pos=<10,12,12>, r=2",
                "pos=<12,14,12>, r=2",
                "pos=<16,12,12>, r=4",
                "pos=<14,14,14>, r=6",
                "pos=<50,50,50>, r=200",
                "pos=<10,10,10>, r=5"));

        assertEquals(36, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day23 day = new Day23();

        assertEquals(399, day.firstStar());
        assertEquals(81396996, day.secondStar());
    }

}
