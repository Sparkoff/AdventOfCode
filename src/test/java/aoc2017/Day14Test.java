package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day14")
class Day14Test {

    @Test
    void test_first_star() {
        Day14 day = new Day14(List.of("flqrgnkx"));

        assertEquals(8108, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day14 day = new Day14(List.of("flqrgnkx"));

        assertEquals(1242, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day14 day = new Day14();

        assertEquals(8106, day.firstStar());
        assertEquals(1164, day.secondStar());
    }

}
