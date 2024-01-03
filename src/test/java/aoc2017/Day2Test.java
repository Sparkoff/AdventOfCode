package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day2")
class Day2Test {

    @Test
    void test_first_star() {
        Day2 day = new Day2(List.of("5 1 9 5",
                "7 5 3",
                "2 4 6 8"));

        assertEquals(18, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day2 day = new Day2(List.of("5 9 2 8",
                "9 4 7 3",
                "3 8 6 5"));

        assertEquals(9, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day2 day = new Day2();

        assertEquals(42378, day.firstStar());
        assertEquals(246, day.secondStar());
    }
}
