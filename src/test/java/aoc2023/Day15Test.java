package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day15")
class Day15Test {

    @Test
    void test_first_star() {
        assertEquals(52, new Day15(List.of("HASH")).firstStar());
        assertEquals(1320, new Day15(List.of("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(145, new Day15(List.of("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day15 day = new Day15();

        assertEquals(516070, day.firstStar());
        assertEquals(244981, day.secondStar());
    }
}
