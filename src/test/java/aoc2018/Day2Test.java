package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day2")
class Day2Test {

    @Test
    void test_first_star() {
        Day2 day = new Day2(List.of("abcdef",
                "bababc",
                "abbcde",
                "abcccd",
                "aabcdd",
                "abcdee",
                "ababab"));

        assertEquals(12, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day2 day = new Day2(List.of("abcde",
                "fghij",
                "klmno",
                "pqrst",
                "fguij",
                "axcye",
                "wvxyz"));

        assertEquals("fgij", day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day2 day = new Day2();

        assertEquals(6150, day.firstStar());
        assertEquals("rteotyxzbodglnpkudawhijsc", day.secondStar());
    }

}