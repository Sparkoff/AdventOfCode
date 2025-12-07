package aoc2017;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day24")
class Day24Test {

    @Test
    void test_first_star() {
        Day24 day = new Day24(List.of("0/2",
                "2/2",
                "2/3",
                "3/4",
                "3/5",
                "0/1",
                "10/1",
                "9/10"));

        assertEquals(31, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day24 day = new Day24(List.of("0/2",
                "2/2",
                "2/3",
                "3/4",
                "3/5",
                "0/1",
                "10/1",
                "9/10"));

        assertEquals(19, day.secondStar());
    }

    @Test
    @Disabled("Test disabled due to long duration: 7sec")
    void test_real_inputs() {
        Day24 day = new Day24();

        assertEquals(1940, day.firstStar());
        assertEquals(1928, day.secondStar());
    }
}
