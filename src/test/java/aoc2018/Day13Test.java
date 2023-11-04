package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day13")
class Day13Test {

    @Test
    void test_first_star() {
        Day13 day = new Day13(List.of("/->-\\        ",
                "|   |  /----\\",
                "| /-+--+-\\  |",
                "| | |  | v  |",
                "\\-+-/  \\-+--/",
                "  \\------/   "));

        assertEquals("7,3", day.firstStar());
    }

    @Test
    void test_second_star() {
        Day13 day = new Day13(List.of("/>-<\\  ",
                "|   |  ",
                "| /<+-\\",
                "| | | v",
                "\\>+</ |",
                "  |   ^",
                "  \\<->/"));

        assertEquals("6,4", day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day13 day = new Day13();

        assertEquals("118,112", day.firstStar());
        assertEquals("50,21", day.secondStar());
    }

}