package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day16")
class Day16Test {

    @Test
    void test_first_star() {
        Day16 day = new Day16(List.of(".|...\\....",
                "|.-.\\.....",
                ".....|-...",
                "........|.",
                "..........",
                ".........\\",
                "..../.\\\\..",
                ".-.-/..|..",
                ".|....-|.\\",
                "..//.|...."));

        assertEquals(46, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day16 day = new Day16(List.of(".|...\\....",
                "|.-.\\.....",
                ".....|-...",
                "........|.",
                "..........",
                ".........\\",
                "..../.\\\\..",
                ".-.-/..|..",
                ".|....-|.\\",
                "..//.|...."));

        assertEquals(51, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day16 day = new Day16();

        assertEquals(6921, day.firstStar());
        assertEquals(7594, day.secondStar());
    }
}
