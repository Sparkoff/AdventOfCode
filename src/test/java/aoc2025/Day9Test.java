package aoc2025;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2025 Day9")
class Day9Test {

    @Test
    void test_first_star() {
        Day9 day = new Day9(List.of("7,1",
            "11,1",
            "11,7",
            "9,7",
            "9,5",
            "2,5",
            "2,3",
            "7,3"));

        assertEquals(50L, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day9 day = new Day9(List.of("7,1",
            "11,1",
            "11,7",
            "9,7",
            "9,5",
            "2,5",
            "2,3",
            "7,3"));

        assertEquals(24L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day9 day = new Day9();

        assertEquals(4729332959L, day.firstStar());
        assertEquals(1474477524L, day.secondStar());
    }
}
