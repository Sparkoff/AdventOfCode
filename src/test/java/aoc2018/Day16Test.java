package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day16")
class Day16Test {

    @Test
    void test_first_star() {
        Day16 day = new Day16(List.of("Before: [3, 2, 1, 1]",
                "9 2 1 2",
                "After:  [3, 2, 2, 1]"));

        assertEquals(1, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day16 day = new Day16();

        assertEquals(646, day.firstStar());
        assertEquals(681, day.secondStar());
    }

}
