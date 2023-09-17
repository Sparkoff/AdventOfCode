package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day19Test {

    @Test
    void test_first_star() {
        Day19 day = new Day19(List.of("     |          ",
                "     |  +--+    ",
                "     A  |  C    ",
                " F---|----E|--+ ",
                "     |  |  |  D ",
                "     +B-+  +--+ "));

        assertEquals("ABCDEF", day.firstStar());
    }

    @Test
    void test_second_star() {
        Day19 day = new Day19(List.of("     |          ",
                "     |  +--+    ",
                "     A  |  C    ",
                " F---|----E|--+ ",
                "     |  |  |  D ",
                "     +B-+  +--+ "));

        assertEquals("38", day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day19 day = new Day19();

        assertEquals("MKXOIHZNBL", day.firstStar());
        assertEquals("17872", day.secondStar());
    }

}
