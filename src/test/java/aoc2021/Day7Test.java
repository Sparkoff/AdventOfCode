package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day7Test {

    @Test
    void test_first_star() {
        Day7 day = new Day7(List.of("16,1,2,0,4,2,7,1,2,14"));

        assertEquals(37, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day7 day = new Day7(List.of("16,1,2,0,4,2,7,1,2,14"));

        assertEquals(168, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day7 day = new Day7();

        assertEquals(339321, day.firstStar());
        assertEquals(95476244, day.secondStar());
    }

}