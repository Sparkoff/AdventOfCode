package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day22")
class Day22Test {

    @Test
    void test_first_star() {
        Day22 day = new Day22(List.of("depth: 510",
                "target: 10,10"));

        assertEquals(114, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day22 day = new Day22(List.of("depth: 510",
                "target: 10,10"));

        assertEquals(45, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day22 day = new Day22();

        assertEquals(9940, day.firstStar());
        assertEquals(944, day.secondStar());
    }
}
