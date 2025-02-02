package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day13")
class Day13Test {

    @Test
    void test_first_star() {
        Day13 day = new Day13(List.of("Button A: X+94, Y+34",
                "Button B: X+22, Y+67",
                "Prize: X=8400, Y=5400",
                "",
                "Button A: X+26, Y+66",
                "Button B: X+67, Y+21",
                "Prize: X=12748, Y=12176",
                "",
                "Button A: X+17, Y+86",
                "Button B: X+84, Y+37",
                "Prize: X=7870, Y=6450",
                "",
                "Button A: X+69, Y+23",
                "Button B: X+27, Y+71",
                "Prize: X=18641, Y=10279"));

        assertEquals(480, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day13 day = new Day13();

        assertEquals(40069, day.firstStar());
        assertEquals(71493195288102L, day.secondStar());
    }
}
