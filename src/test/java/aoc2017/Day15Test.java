package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day15")
class Day15Test {

    @Test
    void test_first_star() {
        Day15 day = new Day15(List.of("Generator A starts with 65",
                "Generator B starts with 8921"));

        assertEquals(588, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day15 day = new Day15(List.of("Generator A starts with 65",
                "Generator B starts with 8921"));

        assertEquals(309, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day15 day = new Day15();

        assertEquals(626, day.firstStar());
        assertEquals(306, day.secondStar());
    }

}
