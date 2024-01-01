package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day7")
class Day7Test {

    @Test
    void test_first_star() {
        Day7 day = new Day7(List.of("Step C must be finished before step A can begin.",
                "Step C must be finished before step F can begin.",
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step D can begin.",
                "Step B must be finished before step E can begin.",
                "Step D must be finished before step E can begin.",
                "Step F must be finished before step E can begin."));

        assertEquals("CABDFE", day.firstStar());
    }

    @Test
    void test_second_star() {
        Day7 day = new Day7(List.of("Step C must be finished before step A can begin.",
                "Step C must be finished before step F can begin.",
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step D can begin.",
                "Step B must be finished before step E can begin.",
                "Step D must be finished before step E can begin.",
                "Step F must be finished before step E can begin."));

        assertEquals(15, day.testMode().secondStar());
    }

    @Test
    void test_real_inputs() {
        Day7 day = new Day7();

        assertEquals("BGKDMJCNEQRSTUZWHYLPAFIVXO", day.firstStar());
        assertEquals(941, day.secondStar());
    }

}