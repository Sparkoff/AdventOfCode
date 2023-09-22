package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day5Test {

    @Test
    void test_first_star() {
        Day5 day = new Day5(List.of("0",
                "3",
                "0",
                "1",
                "-3"));

        assertEquals(5, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day5 day = new Day5(List.of("0",
                "3",
                "0",
                "1",
                "-3"));

        assertEquals(10, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day5 day = new Day5();

        assertEquals(342669, day.firstStar());
        assertEquals(25136209, day.secondStar());
    }

}
