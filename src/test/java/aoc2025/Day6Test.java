package aoc2025;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2025 Day6")
class Day6Test {

    @Test
    void test_first_star() {
        Day6 day = new Day6(List.of("123 328  51 64 ",
                " 45 64  387 23 ",
                "  6 98  215 314",
                "*   +   *   +  "));

        assertEquals(4277556L, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day6 day = new Day6(List.of("123 328  51 64 ",
                " 45 64  387 23 ",
                "  6 98  215 314",
                "*   +   *   +  "));

        assertEquals(3263827L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day6 day = new Day6();

        assertEquals(5733696195703L, day.firstStar());
        assertEquals(10951882745757L, day.secondStar());
    }
}
