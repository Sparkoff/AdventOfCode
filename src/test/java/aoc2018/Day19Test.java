package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day19")
class Day19Test {

    @Test
    void test_first_star() {
        Day19 day = new Day19(List.of("#ip 0",
                "seti 5 0 1",
                "seti 6 0 2",
                "addi 0 1 0",
                "addr 1 2 3",
                "setr 1 0 0",
                "seti 8 0 4",
                "seti 9 0 5"));

        assertEquals(7, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day19 day = new Day19();

        assertEquals(968, day.firstStar());
        assertEquals(10557936, day.secondStar());
    }

}
