package aoc2022;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2022 Day3")
class Day3Test {

    @Test
    void test_items_both_side() {
        assertEquals("p", Day3.getItemsInBothSide("vJrwpWtwJgWrhcsFMMfFFhFp"));
        assertEquals("L", Day3.getItemsInBothSide("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"));
        assertEquals("P", Day3.getItemsInBothSide("PmmdzqPrVvPwwTWBwg"));
        assertEquals("v", Day3.getItemsInBothSide("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"));
        assertEquals("t", Day3.getItemsInBothSide("ttgJtRGJQctTZtZT"));
        assertEquals("s", Day3.getItemsInBothSide("CrZsJsPPZsGzwwsLwLmpwMDw"));
    }

    @Test
    void test_items_values() {
        assertEquals(16, Day3.getItemValue("p"));
        assertEquals(38, Day3.getItemValue("L"));
        assertEquals(42, Day3.getItemValue("P"));
        assertEquals(22, Day3.getItemValue("v"));
        assertEquals(20, Day3.getItemValue("t"));
        assertEquals(19, Day3.getItemValue("s"));
    }

    @Test
    void test_first_star() {
        Day3 day = new Day3(List.of(
                "vJrwpWtwJgWrhcsFMMfFFhFp",
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                "PmmdzqPrVvPwwTWBwg",
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                "ttgJtRGJQctTZtZT",
                "CrZsJsPPZsGzwwsLwLmpwMDw"
        ));

        assertEquals(157, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day3 day = new Day3(List.of(
                "vJrwpWtwJgWrhcsFMMfFFhFp",
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                "PmmdzqPrVvPwwTWBwg",
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                "ttgJtRGJQctTZtZT",
                "CrZsJsPPZsGzwwsLwLmpwMDw"
        ));

        assertEquals(70, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day3 day = new Day3();

        assertEquals(8105, day.firstStar());
        assertEquals(2363, day.secondStar());
    }

}
