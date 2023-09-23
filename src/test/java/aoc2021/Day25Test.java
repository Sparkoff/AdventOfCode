package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("2021 Day25")
class Day25Test {

    @Test
    void test_next_step_linear() {
        Day25.SeaFloor init = new Day25.SeaFloor(
                0,
                11,
                List.of(
                        new Day25.Coord(3, 0),
                        new Day25.Coord(4, 0),
                        new Day25.Coord(5, 0),
                        new Day25.Coord(6, 0),
                        new Day25.Coord(7, 0)
                ),
                List.of()
        );

        Day25.SeaFloor step1 = Day25.computeNextStep(init);
        Day25.SeaFloor step2 = Day25.computeNextStep(step1);

        assertEquals(0, step2.height());
        assertEquals(11, step2.width());
        assertEquals(List.of(
                new Day25.Coord(3, 0),
                new Day25.Coord(4, 0),
                new Day25.Coord(5, 0),
                new Day25.Coord(7, 0),
                new Day25.Coord(9, 0)
        ), step2.east());
        assertTrue(step2.south().isEmpty());
        assertTrue(step2.hasMoved());
    }

    @Test
    void test_next_step_intersection() {
        Day25.SeaFloor init = new Day25.SeaFloor(
                4,
                10,
                List.of(
                        new Day25.Coord(1, 1),
                        new Day25.Coord(7, 2)
                ),
                List.of(
                        new Day25.Coord(2, 1),
                        new Day25.Coord(7, 1)
                )
        );

        Day25.SeaFloor step1 = Day25.computeNextStep(init);

        assertEquals(4, step1.height());
        assertEquals(10, step1.width());
        assertEquals(List.of(
                new Day25.Coord(1, 1),
                new Day25.Coord(8, 2)
        ), step1.east());
        assertEquals(List.of(
                new Day25.Coord(2, 2),
                new Day25.Coord(7, 2)
        ), step1.south());
        assertTrue(step1.hasMoved());
    }

    @Test
    void test_next_step_water_current() {
        Day25.SeaFloor init = new Day25.SeaFloor(
                7,
                7,
                List.of(
                        new Day25.Coord(3, 0),
                        new Day25.Coord(6, 2),
                        new Day25.Coord(6, 3),
                        new Day25.Coord(6, 4)
                ),
                List.of(
                        new Day25.Coord(0, 3),
                        new Day25.Coord(2, 6),
                        new Day25.Coord(3, 6),
                        new Day25.Coord(4, 6)
                )
        );

        Day25.SeaFloor step1 = Day25.computeNextStep(init);
        Day25.SeaFloor step2 = Day25.computeNextStep(step1);
        Day25.SeaFloor step3 = Day25.computeNextStep(step2);
        Day25.SeaFloor step4 = Day25.computeNextStep(step3);

        assertEquals(7, step4.height());
        assertEquals(7, step4.width());
        assertEquals(List.of(
                new Day25.Coord(0, 0),
                new Day25.Coord(2, 2),
                new Day25.Coord(1, 3),
                new Day25.Coord(3, 4)
        ), step4.east());
        assertEquals(List.of(
                new Day25.Coord(0, 6),
                new Day25.Coord(2, 1),
                new Day25.Coord(3, 3),
                new Day25.Coord(4, 2)
        ), step4.south());
        assertTrue(step4.hasMoved());
    }

    @Test
    void test_first_star() {
        Day25 day = new Day25(List.of("v...>>.vv>",
                ".vv>>.vv..",
                ">>.>v>...v",
                ">>v>>.>.v.",
                "v>v.vv.v..",
                ">.>>..v...",
                ".vv..>.>v.",
                "v.v..>>v.v",
                "....v..v.>"));

        assertEquals(58, day.firstStar());
    }

    @Test
    void test_real_inputs() {
        Day25 day = new Day25();

        assertEquals(367, day.firstStar());
        assertEquals(2021, day.secondStar());
    }

}