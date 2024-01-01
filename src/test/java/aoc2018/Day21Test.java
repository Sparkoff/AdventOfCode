package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day21")
class Day21Test {

    @Test
    void test_real_inputs() {
        Day21 day = new Day21();

        assertEquals(10961197, day.firstStar());
        assertEquals(8164934, day.secondStar());
    }

}
