package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day8")
class Day8Test {

    @Test
    void test_first_star() {
        assertEquals(2, new Day8(List.of("RL",
                "",
                "AAA = (BBB, CCC)",
                "BBB = (DDD, EEE)",
                "CCC = (ZZZ, GGG)",
                "DDD = (DDD, DDD)",
                "EEE = (EEE, EEE)",
                "GGG = (GGG, GGG)",
                "ZZZ = (ZZZ, ZZZ)")).firstStar());
        assertEquals(6, new Day8(List.of("LLR",
                "",
                "AAA = (BBB, BBB)",
                "BBB = (AAA, ZZZ)",
                "ZZZ = (ZZZ, ZZZ)")).firstStar());
    }

    @Test
    void test_second_star() {
        Day8 day = new Day8(List.of("LR",
                "",
                "11A = (11B, XXX)",
                "11B = (XXX, 11Z)",
                "11Z = (11B, XXX)",
                "22A = (22B, XXX)",
                "22B = (22C, 22C)",
                "22C = (22Z, 22Z)",
                "22Z = (22B, 22B)",
                "XXX = (XXX, XXX)"));

        assertEquals(6, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day8 day = new Day8();

        assertEquals(15517, day.firstStar());
        assertEquals(14935034899483L, day.secondStar());
    }
}
