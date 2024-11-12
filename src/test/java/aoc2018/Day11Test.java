package aoc2018;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day11")
class Day11Test {

    @Test
    void test_fuel_cell_at() {
        assertEquals(4, Day11.fuelCellAt(3, 5, 8));
        assertEquals(-5, Day11.fuelCellAt(122, 79, 57));
        assertEquals(0, Day11.fuelCellAt(217, 196, 39));
        assertEquals(4, Day11.fuelCellAt(101, 153, 71));
    }

    @Test
    void test_first_star() {
        assertEquals("33,45", new Day11(List.of("18")).firstStar());
        assertEquals("21,61", new Day11(List.of("42")).firstStar());
    }

    @Test
    @Disabled  // 33sec
    void test_second_star() {
        assertEquals("90,269,16", new Day11(List.of("18")).secondStar());
        assertEquals("232,251,12", new Day11(List.of("42")).secondStar());
    }

    @Test
    @Disabled  // 16sec
    void test_real_inputs() {
        Day11 day = new Day11();

        assertEquals("19,17", day.firstStar());
        assertEquals("233,288,12", day.secondStar());
    }
}
