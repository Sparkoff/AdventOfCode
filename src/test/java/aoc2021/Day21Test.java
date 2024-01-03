package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day21")
class Day21Test {

    @Test
    void test_first_star() {
        Day21 day = new Day21(List.of("Player 1 starting position: 4",
                "Player 2 starting position: 8"));

        assertEquals(739785L, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day21 day = new Day21(List.of("Player 1 starting position: 4",
                "Player 2 starting position: 8"));

        assertEquals(444356092776315L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day21 day = new Day21();

        assertEquals(576600L, day.firstStar());
        assertEquals(131888061854776L, day.secondStar());
    }
}
