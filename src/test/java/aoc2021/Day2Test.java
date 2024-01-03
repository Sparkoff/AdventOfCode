package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day2")
class Day2Test {

    @Test
    void test_first_star() {
        Day2 day = new Day2(List.of("forward 5", "down 5", "forward 8", "up 3",
                "down 8", "forward 2"));

        assertEquals(150, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day2 day = new Day2(List.of("forward 5", "down 5", "forward 8", "up 3",
                "down 8", "forward 2"));

        assertEquals(900, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day2 day = new Day2();

        assertEquals(2215080, day.firstStar());
        assertEquals(1864715580, day.secondStar());
    }
}
