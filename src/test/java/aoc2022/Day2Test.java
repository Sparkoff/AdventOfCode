package aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2022 Day2")
class Day2Test {

    @Test
    void test_first_star() {
        Day2 day = new Day2(List.of(
                "A Y",
                "B X",
                "C Z"
        ));

        assertEquals(15, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day2 day = new Day2(List.of(
                "A Y",
                "B X",
                "C Z"
        ));

        assertEquals(12, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day2 day = new Day2();

        assertEquals(15632, day.firstStar());
        assertEquals(14416, day.secondStar());
    }

}
