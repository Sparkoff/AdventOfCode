package aoc2025;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2025 Day2")
class Day2Test {

    @Test
    void test_first_star() {
        Day2 day = new Day2(List.of("11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                "824824821-824824827,2121212118-2121212124"));

        assertEquals(1227775554L, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day2 day = new Day2(List.of("11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                "824824821-824824827,2121212118-2121212124"));

        assertEquals(4174379265L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day2 day = new Day2();

        assertEquals(23039913998L, day.firstStar());
        assertEquals(35950619148L, day.secondStar());
    }
}
