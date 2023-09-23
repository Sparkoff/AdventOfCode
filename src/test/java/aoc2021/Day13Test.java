package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@DisplayName("2021 Day13")
class Day13Test {

    @Test
    void test_first_star() {
        Day13 day = new Day13(List.of("6,10",
                "0,14",
                "9,10",
                "0,3",
                "10,4",
                "4,11",
                "6,0",
                "6,12",
                "4,1",
                "0,13",
                "10,12",
                "3,4",
                "3,0",
                "8,4",
                "1,10",
                "2,14",
                "8,10",
                "9,0",
                "",
                "fold along y=7",
                "fold along x=5"
        ));

        assertEquals(17, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day13 day = new Day13(List.of("6,10",
                "0,14",
                "9,10",
                "0,3",
                "10,4",
                "4,11",
                "6,0",
                "6,12",
                "4,1",
                "0,13",
                "10,12",
                "3,4",
                "3,0",
                "8,4",
                "1,10",
                "2,14",
                "8,10",
                "9,0",
                "",
                "fold along y=7",
                "fold along x=5"
        ));

        assertNull(day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day13 day = new Day13();

        assertEquals(763, day.firstStar());
        assertNull(day.secondStar()); //RHALRCRA
    }

}