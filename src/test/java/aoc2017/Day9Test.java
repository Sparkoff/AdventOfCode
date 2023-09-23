package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day9")
class Day9Test {

    @Test
    void test_first_star() {
        assertEquals(1, (new Day9(List.of("{}"))).firstStar());
        assertEquals(6, (new Day9(List.of("{{{}}}"))).firstStar());
        assertEquals(5, (new Day9(List.of("{{},{}}"))).firstStar());
        assertEquals(16, (new Day9(List.of("{{{},{},{{}}}}"))).firstStar());
        assertEquals(1, (new Day9(List.of("{<a>,<a>,<a>,<a>}"))).firstStar());
        assertEquals(9, (new Day9(List.of("{{<ab>},{<ab>},{<ab>},{<ab>}}"))).firstStar());
        assertEquals(9, (new Day9(List.of("{{<!!>},{<!!>},{<!!>},{<!!>}}"))).firstStar());
        assertEquals(3, (new Day9(List.of("{{<a!>},{<a!>},{<a!>},{<ab>}}"))).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(0, (new Day9(List.of("{<>}"))).secondStar());
        assertEquals(17, (new Day9(List.of("{<random characters>}"))).secondStar());
        assertEquals(3, (new Day9(List.of("{<<<<>}"))).secondStar());
        assertEquals(2, (new Day9(List.of("{<{!>}>}"))).secondStar());
        assertEquals(0, (new Day9(List.of("{<!!>}"))).secondStar());
        assertEquals(0, (new Day9(List.of("{<!!!>>}"))).secondStar());
        assertEquals(10, (new Day9(List.of("{<{o\"i!a,<{i<a>}"))).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day9 day = new Day9();

        assertEquals(12505, day.firstStar());
        assertEquals(6671, day.secondStar());
    }

}
