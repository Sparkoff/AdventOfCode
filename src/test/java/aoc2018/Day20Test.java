package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day20")
class Day20Test {

    @Test
    void test_first_star_basic() {
        Day20 day = new Day20(List.of("^WNE$"));

        assertEquals(3, day.firstStar());
    }

    @Test
    void test_first_star_alternate() {
        Day20 day = new Day20(List.of("^ENWWW(NEEE|SSE(EE|N))$"));

        assertEquals(10, day.firstStar());
    }

    @Test
    void test_first_star_dead_end() {
        Day20 day = new Day20(List.of("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$"));

        assertEquals(18, day.firstStar());
    }

    @Test
    void test_first_star_complex() {
        assertEquals(23, new Day20(List.of("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$")).firstStar());
        assertEquals(31, new Day20(List.of("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$")).firstStar());
    }

    @Test
    void test_real_inputs() {
        Day20 day = new Day20();

        assertEquals(4214, day.firstStar());
        assertEquals(8492, day.secondStar());
    }
}
