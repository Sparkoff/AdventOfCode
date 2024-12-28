package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day3")
class Day3Test {

    @Test
    void test_first_star() {
        Day3 day = new Day3(List.of("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"));

        assertEquals(161, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day3 day = new Day3(List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"));

        assertEquals(48, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day3 day = new Day3();

        assertEquals(191183308, day.firstStar());
        assertEquals(92082041, day.secondStar());
    }
}
