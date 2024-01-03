package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day1")
class Day1Test {

    @Test
    void test_first_star_simple_match() {
        Day1 day = new Day1(List.of("1122"));

        assertEquals(3, day.firstStar());
    }

    @Test
    void test_first_star_all_match() {
        Day1 day = new Day1(List.of("1111"));

        assertEquals(4, day.firstStar());
    }

    @Test
    void test_first_star_none_match() {
        Day1 day = new Day1(List.of("1234"));

        assertEquals(0, day.firstStar());
    }

    @Test
    void test_first_star_circular_match() {
        Day1 day = new Day1(List.of("91212129"));

        assertEquals(9, day.firstStar());
    }

    @Test
    void test_second_star_simple_match() {
        Day1 day = new Day1(List.of("1212"));

        assertEquals(6, day.secondStar());
    }

    @Test
    void test_second_star_none_match() {
        Day1 day = new Day1(List.of("1221"));

        assertEquals(0, day.secondStar());
    }

    @Test
    void test_second_star_picking_match() {
        Day1 day = new Day1(List.of("123425"));

        assertEquals(4, day.secondStar());
    }

    @Test
    void test_second_star_all_match() {
        Day1 day = new Day1(List.of("123123"));

        assertEquals(12, day.secondStar());
    }

    @Test
    void test_second_star_multiple_picking_match() {
        Day1 day = new Day1(List.of("12131415"));

        assertEquals(4, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day1 day = new Day1();

        assertEquals(1034, day.firstStar());
        assertEquals(1356, day.secondStar());
    }
}
