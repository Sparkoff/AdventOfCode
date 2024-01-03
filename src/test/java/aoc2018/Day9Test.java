package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day9")
class Day9Test {

    @Test
    void test_first_star() {
        assertEquals(32, new Day9(List.of("9 players; last marble is worth 25 points")).firstStar());
        assertEquals(8317, new Day9(List.of("10 players; last marble is worth 1618 points")).firstStar());
        assertEquals(146373, new Day9(List.of("13 players; last marble is worth 7999 points")).firstStar());
        assertEquals(2764, new Day9(List.of("17 players; last marble is worth 1104 points")).firstStar());
        assertEquals(54718, new Day9(List.of("21 players; last marble is worth 6111 points")).firstStar());
        assertEquals(37305, new Day9(List.of("30 players; last marble is worth 5807 points")).firstStar());
    }

    @Test
    void test_real_inputs() {
        Day9 day = new Day9();

        assertEquals(361466, day.firstStar());
        assertEquals(2945918550L, day.secondStar());
    }
}
