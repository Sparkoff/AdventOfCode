package aoc2017;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day23Test {

    @Test
    void test_real_inputs() {
        Day23 day = new Day23();

        assertEquals(9409, day.firstStar());
        assertEquals(913, day.secondStar());
    }

}
