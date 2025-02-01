package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day11")
class Day11Test {

    @Test
    void test_first_star() {
        Day11 day = new Day11(List.of("125 17"));

        assertEquals(55312, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day11 day = new Day11();

        assertEquals(186175, day.firstStar());
        assertEquals(220566831337810L, day.secondStar());
    }
}
