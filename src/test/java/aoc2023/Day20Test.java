package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day20")
class Day20Test {

    @Test
    void test_first_star() {
        assertEquals(32000000, new Day20(List.of("broadcaster -> a, b, c",
                "%a -> b",
                "%b -> c",
                "%c -> inv",
                "&inv -> a")).firstStar());
        assertEquals(11687500, new Day20(List.of("broadcaster -> a",
                "%a -> inv, con",
                "&inv -> b",
                "%b -> con",
                "&con -> output")).firstStar());
    }

    @Test
    void test_real_inputs() {
        Day20 day = new Day20();

        assertEquals(666795063, day.firstStar());
        assertEquals(253302889093151L, day.secondStar());
    }
}
