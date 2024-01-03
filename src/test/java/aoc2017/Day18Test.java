package aoc2017;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2017 Day18")
class Day18Test {

    @Test
    void test_first_star() {
        Day18 day = new Day18(List.of("set a 1",
                "add a 2",
                "mul a a",
                "mod a 5",
                "snd a",
                "set a 0",
                "rcv a",
                "jgz a -1",
                "set a 1",
                "jgz a -2"));

        assertEquals(4, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day18 day = new Day18(List.of("snd 1",
                "snd 2",
                "snd p",
                "rcv a",
                "rcv b",
                "rcv c",
                "rcv d"));

        assertEquals(3, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day18 day = new Day18();

        assertEquals(7071, day.firstStar());
        assertEquals(8001, day.secondStar());
    }
}
