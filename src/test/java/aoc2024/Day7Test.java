package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day7")
class Day7Test {

    @Test
    void test_first_star() {
        Day7 day = new Day7(List.of("190: 10 19",
                "3267: 81 40 27",
                "83: 17 5",
                "156: 15 6",
                "7290: 6 8 6 15",
                "161011: 16 10 13",
                "192: 17 8 14",
                "21037: 9 7 18 13",
                "292: 11 6 16 20"));

        assertEquals(3749L, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day7 day = new Day7(List.of("190: 10 19",
                "3267: 81 40 27",
                "83: 17 5",
                "156: 15 6",
                "7290: 6 8 6 15",
                "161011: 16 10 13",
                "192: 17 8 14",
                "21037: 9 7 18 13",
                "292: 11 6 16 20"));

        assertEquals(11387L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day7 day = new Day7();

        assertEquals(3119088655389L, day.firstStar());
        assertEquals(264184041398847L, day.secondStar());
    }
}
