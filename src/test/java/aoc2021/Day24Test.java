package aoc2021;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2021 Day24")
class Day24Test {

    @Test
    void test_execution_model() {
        Day24 day = new Day24();

        assertEquals(day.executeRawInput(day.getInput(Day24::parseMONAD), 15236985741256L), day.executeInterpretedInput(15236985741256L));
        assertEquals(day.executeRawInput(day.getInput(Day24::parseMONAD), 25486587413629L), day.executeInterpretedInput(25486587413629L));
        assertEquals(day.executeRawInput(day.getInput(Day24::parseMONAD), 96855236587412L), day.executeInterpretedInput(96855236587412L));
        assertEquals(day.executeRawInput(day.getInput(Day24::parseMONAD), 65874236524198L), day.executeInterpretedInput(65874236524198L));
    }

    @Test
    void test_real_inputs() {
        Day24 day = new Day24();

        assertEquals(65984919997939L, day.firstStar());
        assertEquals(11211619541713L, day.secondStar());
    }
}
