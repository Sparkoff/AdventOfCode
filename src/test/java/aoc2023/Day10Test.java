package aoc2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2023 Day10")
class Day10Test {

    @Test
    void test_first_star() {
        assertEquals(4, new Day10(List.of(".....",
                ".S-7.",
                ".|.|.",
                ".L-J.",
                ".....")).firstStar());
        assertEquals(8, new Day10(List.of("..F7.",
                ".FJ|.",
                "SJ.L7",
                "|F--J",
                "LJ...")).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(4, new Day10(List.of("...........",
                ".S-------7.",
                ".|F-----7|.",
                ".||.....||.",
                ".||.....||.",
                ".|L-7.F-J|.",
                ".|..|.|..|.",
                ".L--J.L--J.",
                "...........")).secondStar());
        assertEquals(4, new Day10(List.of("..........",
                ".S------7.",
                ".|F----7|.",
                ".||....||.",
                ".||....||.",
                ".|L-7F-J|.",
                ".|..||..|.",
                ".L--JL--J.",
                "..........")).secondStar());
        assertEquals(8, new Day10(List.of(".F----7F7F7F7F-7....",
                ".|F--7||||||||FJ....",
                ".||.FJ||||||||L7....",
                "FJL7L7LJLJ||LJ.L-7..",
                "L--J.L7...LJS7F-7L7.",
                "....F-J..F7FJ|L7L7L7",
                "....L7.F7||L7|.L7L7|",
                ".....|FJLJ|FJ|F7|.LJ",
                "....FJL-7.||.||||...",
                "....L---J.LJ.LJLJ...")).secondStar());
        assertEquals(10, new Day10(List.of("FF7FSF7F7F7F7F7F---7",
                "L|LJ||||||||||||F--J",
                "FL-7LJLJ||||||LJL-77",
                "F--JF--7||LJLJ7F7FJ-",
                "L---JF-JLJ.||-FJLJJ7",
                "|F|F-JF---7F7-L7L|7|",
                "|FFJF7L7F-JF7|JL---7",
                "7-L-JL7||F7|L7F-7F7|",
                "L.L7LFJ|||||FJL7||LJ",
                "L7JLJL-JLJLJL--JLJ.L")).secondStar());
    }

    @Test
    void test_real_inputs() {
        Day10 day = new Day10();

        assertEquals(6800, day.firstStar());
        assertEquals(483, day.secondStar());
    }
}
