package aoc2025;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2025 Day5")
class Day5Test {

    @Test
    void test_first_star() {
        Day5 day = new Day5(List.of("3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "1",
                "5",
                "8",
                "11",
                "17",
                "32"));

        assertEquals(3, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day5 day = new Day5(List.of("3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "1",
                "5",
                "8",
                "11",
                "17",
                "32"));

        assertEquals(14, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day5 day = new Day5();

        assertEquals(701, day.firstStar());
        assertEquals(352340558684863L, day.secondStar());
    }
}
