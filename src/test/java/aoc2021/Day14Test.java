package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day14")
class Day14Test {

    @Test
    void test_first_star() {
        Day14 day = new Day14(List.of("NNCB",
                "",
                "CH -> B",
                "HH -> N",
                "CB -> H",
                "NH -> C",
                "HB -> C",
                "HC -> B",
                "HN -> C",
                "NN -> C",
                "BH -> H",
                "NC -> B",
                "NB -> B",
                "BN -> B",
                "BB -> N",
                "BC -> B",
                "CC -> N",
                "CN -> C"
        ));

        assertEquals(1588L, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day14 day = new Day14(List.of("NNCB",
                "",
                "CH -> B",
                "HH -> N",
                "CB -> H",
                "NH -> C",
                "HB -> C",
                "HC -> B",
                "HN -> C",
                "NN -> C",
                "BH -> H",
                "NC -> B",
                "NB -> B",
                "BN -> B",
                "BB -> N",
                "BC -> B",
                "CC -> N",
                "CN -> C"
        ));

        assertEquals(2188189693529L, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day14 day = new Day14();

        assertEquals(3009L, day.firstStar());
        assertEquals(3459822539451L, day.secondStar());
    }
}
