package aoc2017;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day7Test {

    @Test
    void test_first_star() {
        Day7 day = new Day7(List.of("pbga (66)",
                "xhth (57)",
                "ebii (61)",
                "havc (66)",
                "ktlj (57)",
                "fwft (72) -> ktlj, cntj, xhth",
                "qoyq (66)",
                "padx (45) -> pbga, havc, qoyq",
                "tknk (41) -> ugml, padx, fwft",
                "jptl (61)",
                "ugml (68) -> gyxo, ebii, jptl",
                "gyxo (61)",
                "cntj (57)"));

        assertEquals("tknk", day.firstStar());
    }

    @Test
    void test_second_star() {
        Day7 day = new Day7(List.of("pbga (66)",
                "xhth (57)",
                "ebii (61)",
                "havc (66)",
                "ktlj (57)",
                "fwft (72) -> ktlj, cntj, xhth",
                "qoyq (66)",
                "padx (45) -> pbga, havc, qoyq",
                "tknk (41) -> ugml, padx, fwft",
                "jptl (61)",
                "ugml (68) -> gyxo, ebii, jptl",
                "gyxo (61)",
                "cntj (57)"));

        assertEquals(60, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day7 day = new Day7();

        assertEquals("cyrupz", day.firstStar());
        assertEquals(193, day.secondStar());
    }

}
