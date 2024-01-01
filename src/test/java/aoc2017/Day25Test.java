package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day25")
class Day25Test {

    @Test
    void test_first_star() {
        Day25 day = new Day25(List.of("Begin in state A.",
                "Perform a diagnostic checksum after 6 steps.",
                "",
                "In state A:",
                "  If the current value is 0:",
                "    - Write the value 1.",
                "    - Move one slot to the right.",
                "    - Continue with state B.",
                "  If the current value is 1:",
                "    - Write the value 0.",
                "    - Move one slot to the left.",
                "    - Continue with state B.",
                "",
                "In state B:",
                "  If the current value is 0:",
                "    - Write the value 1.",
                "    - Move one slot to the left.",
                "    - Continue with state A.",
                "  If the current value is 1:",
                "    - Write the value 1.",
                "    - Move one slot to the right.",
                "    - Continue with state A."));

        assertEquals(3, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day25 day = new Day25(List.of());

        assertEquals(2017, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day25 day = new Day25();

        assertEquals(5744, day.firstStar());
        assertEquals(2017, day.secondStar());
    }

}
