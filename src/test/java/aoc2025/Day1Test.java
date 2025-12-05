package aoc2025;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2025 Day1")
class Day1Test {

    @Test
    void test_first_star() {
        aoc2025.Day1 day = new aoc2025.Day1(List.of("L68",
                "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82"));

        assertEquals(3, day.firstStar());
    }

    @Test
    void test_second_star() {
        aoc2025.Day1 day = new aoc2025.Day1(List.of("L68",
                "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82"));

        assertEquals(6, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        aoc2025.Day1 day = new Day1();

        assertEquals(1031, day.firstStar());
        assertEquals(5831, day.secondStar());
    }
}
