package aoc2021;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day12Test {

    @Test
    void test_first_star_small() {
        Day12 day = new Day12(List.of("start-A",
                "start-b",
                "A-c",
                "A-b",
                "b-d",
                "A-end",
                "b-end"
        ));

        assertEquals(10, day.firstStar());
    }

    @Test
    void test_first_star_medium() {
        Day12 day = new Day12(List.of("dc-end",
                "HN-start",
                "start-kj",
                "dc-start",
                "dc-HN",
                "LN-dc",
                "HN-end",
                "kj-sa",
                "kj-HN",
                "kj-dc"
        ));

        assertEquals(19, day.firstStar());
    }

    @Test
    void test_first_star_large() {
        Day12 day = new Day12(List.of("fs-end",
                "he-DX",
                "fs-he",
                "start-DX",
                "pj-DX",
                "end-zg",
                "zg-sl",
                "zg-pj",
                "pj-he",
                "RW-he",
                "fs-DX",
                "pj-RW",
                "zg-RW",
                "start-pj",
                "he-WI",
                "zg-he",
                "pj-fs",
                "start-RW"
        ));

        assertEquals(226, day.firstStar());
    }

    @Test
    void test_second_star_small() {
        Day12 day = new Day12(List.of("start-A",
                "start-b",
                "A-c",
                "A-b",
                "b-d",
                "A-end",
                "b-end"
        ));

        assertEquals(36, day.secondStar());
    }

    @Test
    void test_second_star_medium() {
        Day12 day = new Day12(List.of("dc-end",
                "HN-start",
                "start-kj",
                "dc-start",
                "dc-HN",
                "LN-dc",
                "HN-end",
                "kj-sa",
                "kj-HN",
                "kj-dc"
        ));

        assertEquals(103, day.secondStar());
    }

    @Test
    void test_second_star_large() {
        Day12 day = new Day12(List.of("fs-end",
                "he-DX",
                "fs-he",
                "start-DX",
                "pj-DX",
                "end-zg",
                "zg-sl",
                "zg-pj",
                "pj-he",
                "RW-he",
                "fs-DX",
                "pj-RW",
                "zg-RW",
                "start-pj",
                "he-WI",
                "zg-he",
                "pj-fs",
                "start-RW"
        ));

        assertEquals(3509, day.secondStar());
    }

    @Test
    @Disabled
    void test_real_inputs() {
        Day12 day = new Day12();

        assertEquals(3230, day.firstStar());
        assertEquals(83475, day.secondStar());
    }

}