package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day16Test {

    @Test
    void test_first_star() {
        Day16 day = new Day16(List.of("s1,x3/4,pe/b"));

        assertEquals("baedc", day.firstStar());
    }

    @Test
    void test_second_star() {
        Day16 day = new Day16(List.of("s1,x3/4,pe/b"));

        assertEquals("ceadb", day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day16 day = new Day16();

        assertEquals("pkgnhomelfdibjac", day.firstStar());
        assertEquals("pogbjfihclkemadn", day.secondStar());
    }

}
