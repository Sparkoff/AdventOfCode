package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day8")
class Day8Test {

    @Test
    void test_first_star() {
        Day8 day = new Day8(List.of("b inc 5 if a > 1",
                "a inc 1 if b < 5",
                "c dec -10 if a >= 1",
                "c inc -20 if c == 10"));

        assertEquals(1, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day8 day = new Day8(List.of("b inc 5 if a > 1",
                "a inc 1 if b < 5",
                "c dec -10 if a >= 1",
                "c inc -20 if c == 10"));

        assertEquals(10, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day8 day = new Day8();

        assertEquals(4567, day.firstStar());
        assertEquals(5636, day.secondStar());
    }

}
