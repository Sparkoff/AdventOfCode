package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day5")
class Day5Test {

    @Test
    void test_first_star() {
        Day5 day = new Day5(List.of("0,9 -> 5,9",
                "8,0 -> 0,8",
                "9,4 -> 3,4",
                "2,2 -> 2,1",
                "7,0 -> 7,4",
                "6,4 -> 2,0",
                "0,9 -> 2,9",
                "3,4 -> 1,4",
                "0,0 -> 8,8",
                "5,5 -> 8,2"));

        assertEquals(5, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day5 day = new Day5(List.of("0,9 -> 5,9",
                "8,0 -> 0,8",
                "9,4 -> 3,4",
                "2,2 -> 2,1",
                "7,0 -> 7,4",
                "6,4 -> 2,0",
                "0,9 -> 2,9",
                "3,4 -> 1,4",
                "0,0 -> 8,8",
                "5,5 -> 8,2"));

        assertEquals(12, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day5 day = new Day5();

        assertEquals(6267, day.firstStar());
        assertEquals(20196, day.secondStar());
    }

}