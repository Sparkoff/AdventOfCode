package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day9")
class Day9Test {

    @Test
    void test_first_star() {
        Day9 day = new Day9(List.of("2199943210",
                "3987894921",
                "9856789892",
                "8767896789",
                "9899965678"
        ));

        assertEquals(15, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day9 day = new Day9(List.of("2199943210",
                "3987894921",
                "9856789892",
                "8767896789",
                "9899965678"
        ));

        assertEquals(1134, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day9 day = new Day9();

        assertEquals(456, day.firstStar());
        assertEquals(1047744, day.secondStar());
    }
}
