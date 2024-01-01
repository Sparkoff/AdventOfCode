package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day1")
class Day1Test {

    @Test
    void test_first_star() {
        Day1 day = new Day1(List.of("199", "200", "208", "210", "200", "207",
                "240", "269", "260", "263"));

        assertEquals(7, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day1 day = new Day1(List.of("199", "200", "208", "210", "200", "207",
                "240", "269", "260", "263"));

        assertEquals(5, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day1 day = new Day1();

        assertEquals(1521, day.firstStar());
        assertEquals(1543, day.secondStar());
    }

}