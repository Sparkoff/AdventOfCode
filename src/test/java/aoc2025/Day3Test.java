package aoc2025;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2025 Day3")
class Day3Test {

    @Test
    void test_first_star() {
        Day3 day = new Day3(List.of("987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111"));

        assertEquals(357, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day3 day = new Day3(List.of("987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111"));

        assertEquals(3121910778619L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day3 day = new Day3();

        assertEquals(17144, day.firstStar());
        assertEquals(170371185255900L, day.secondStar());
    }
}
