package aoc2018;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;


public class Day5 extends DayBase<String, Integer, Integer> {

    public Day5() {
        super();
    }

    public Day5(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        String polymer = this.getInput(PuzzleInput::asString);

        return react(polymer).length();
    }

    @Override
    public Integer secondStar() {
        String polymer = this.getInput(PuzzleInput::asString);

        List<String> units = Arrays.stream(polymer.toLowerCase().split(""))
                .distinct()
                .toList();

        int min = Integer.MAX_VALUE;
        for (String unit : units) {
            min = Math.min(min, react(polymer.replaceAll("[" + unit + unit.toUpperCase() + "]", "")).length());
        }

        return min;
    }

    private static String react(String polymer) {
        StringBuilder reacted = new StringBuilder();
        for (int i = 0; i < polymer.length(); i++) {
            if (reacted.isEmpty()) {
                reacted.append(polymer.charAt(i));
            } else if ((reacted.charAt(reacted.length() - 1) ^ polymer.charAt(i)) == 32) {
                reacted.deleteCharAt(reacted.length() - 1);
            } else {
                reacted.append(polymer.charAt(i));
            }
        }
        return reacted.toString();
    }

}
