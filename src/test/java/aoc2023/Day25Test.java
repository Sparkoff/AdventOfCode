package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day25")
class Day25Test {

    @Test
    void test_first_star() {
        Day25 day = new Day25(List.of("jqt: rhn xhk nvd",
                "rsh: frs pzl lsr",
                "xhk: hfx",
                "cmg: qnr nvd lhk bvb",
                "rhn: xhk bvb hfx",
                "bvb: xhk hfx",
                "pzl: lsr hfx nvd",
                "qnr: nvd",
                "ntq: jqt hfx bvb xhk",
                "nvd: lhk",
                "lsr: lhk",
                "rzs: qnr cmg lsr rsh",
                "frs: qnr lhk lsr"));

        assertEquals(54, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day25 day = new Day25();

        assertEquals(547410, day.firstStar());
        assertEquals(2023, day.secondStar());
    }
}
