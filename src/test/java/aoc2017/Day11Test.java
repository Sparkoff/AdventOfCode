package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day11")
class Day11Test {

    @Test
    void test_first_star() {
        assertEquals(3, (new Day11(List.of("ne,ne,ne"))).firstStar());
        assertEquals(0, (new Day11(List.of("ne,ne,sw,sw"))).firstStar());
        assertEquals(2, (new Day11(List.of("ne,ne,s,s"))).firstStar());
        assertEquals(3, (new Day11(List.of("se,sw,se,sw,sw"))).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(3, (new Day11(List.of("ne,ne,ne"))).secondStar());
        assertEquals(2, (new Day11(List.of("ne,ne,sw,sw"))).secondStar());
        assertEquals(2, (new Day11(List.of("ne,ne,s,s"))).secondStar());
        assertEquals(3, (new Day11(List.of("se,sw,se,sw,sw"))).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day11 day = new Day11();

        assertEquals(773, day.firstStar());
        assertEquals(1560, day.secondStar());
    }

}
