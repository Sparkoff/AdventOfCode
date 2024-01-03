package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day10")
class Day10Test {

    @Test
    void test_first_star() {
        Day10 day = new Day10(List.of("3,4,1,5"));
        day.setHashSize(5);

        assertEquals(12, day.firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", (new Day10(List.of(""))).secondStar());
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", (new Day10(List.of("AoC 2017"))).secondStar());
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", (new Day10(List.of("1,2,3"))).secondStar());
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", (new Day10(List.of("1,2,4"))).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day10 day = new Day10();

        assertEquals(29240, day.firstStar());
        assertEquals("4db3799145278dc9f73dcdbc680bd53d", day.secondStar());
    }
}
