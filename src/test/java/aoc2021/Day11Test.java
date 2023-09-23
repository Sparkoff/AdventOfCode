package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day11")
class Day11Test {

    @Test
    void test_first_star() {
        Day11 day = new Day11(List.of("5483143223",
                "2745854711",
                "5264556173",
                "6141336146",
                "6357385478",
                "4167524645",
                "2176841721",
                "6882881134",
                "4846848554",
                "5283751526"
        ));

        assertEquals(1656, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day11 day = new Day11(List.of("5483143223",
                "2745854711",
                "5264556173",
                "6141336146",
                "6357385478",
                "4167524645",
                "2176841721",
                "6882881134",
                "4846848554",
                "5283751526"
        ));

        assertEquals(195, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day11 day = new Day11();

        assertEquals(1721, day.firstStar());
        assertEquals(298, day.secondStar());
    }

}