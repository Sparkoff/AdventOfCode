package aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2022 Day1")
class Day1Test {

    @Test
    void test_first_star() {
        Day1 day = new Day1(List.of(
                "1000",
                "2000",
                "3000",
                "",
                "4000",
                "",
                "5000",
                "6000",
                "",
                "7000",
                "8000",
                "9000",
                "",
                "10000"
        ));

        assertEquals(24000, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day1 day = new Day1(List.of(
                "1000",
                "2000",
                "3000",
                "",
                "4000",
                "",
                "5000",
                "6000",
                "",
                "7000",
                "8000",
                "9000",
                "",
                "10000"
        ));

        assertEquals(45000, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day1 day = new Day1();

        assertEquals(71502, day.firstStar());
        assertEquals(208191, day.secondStar());
    }

}
