package aoc2018;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day24")
class Day24Test {

    @Test
    void test_first_star() {
        Day24 day = new Day24(List.of("Immune System:",
                "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
                "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3",
                "",
                "Infection:",
                "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
                "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"));

        assertEquals(5216, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day24 day = new Day24(List.of("Immune System:",
                "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
                "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3",
                "",
                "Infection:",
                "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
                "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"));

        assertEquals(51, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day24 day = new Day24();

        assertEquals(14377, day.firstStar());
        assertEquals(6947, day.secondStar());
    }

}
