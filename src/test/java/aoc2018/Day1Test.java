package aoc2018;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day1Test {

    @Test
    void test_first_star() {
        assertEquals(3, new Day1(List.of("+1", "-2", "+3", "+1")).firstStar());
        assertEquals(3, new Day1(List.of("+1", "+1", "+1")).firstStar());
        assertEquals(0, new Day1(List.of("+1", "+1", "-2")).firstStar());
        assertEquals(-6, new Day1(List.of("-1", "-2", "-3")).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(2, new Day1(List.of("+1", "-2", "+3", "+1")).secondStar());
        assertEquals(0, new Day1(List.of("+1", "-1")).secondStar());
        assertEquals(10, new Day1(List.of("+3", "+3", "+4", "-2", "-4")).secondStar());
        assertEquals(5, new Day1(List.of("-6", "+3", "+8", "+5", "-6")).secondStar());
        assertEquals(14, new Day1(List.of("+7", "+7", "-2", "-7", "-4")).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day1 day = new Day1();

        assertEquals(477, day.firstStar());
        assertEquals(390, day.secondStar());
    }

}
