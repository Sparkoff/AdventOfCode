package aoc2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2024 Day10")
class Day10Test {

    @Test
    void test_first_star() {
        Day10 day = new Day10(List.of("89010123",
                "78121874",
                "87430965",
                "96549874",
                "45678903",
                "32019012",
                "01329801",
                "10456732"));

        assertEquals(36, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day10 day = new Day10(List.of("89010123",
                "78121874",
                "87430965",
                "96549874",
                "45678903",
                "32019012",
                "01329801",
                "10456732"));

        assertEquals(81, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day10 day = new Day10();

        assertEquals(789, day.firstStar());
        assertEquals(1735, day.secondStar());
    }
}
