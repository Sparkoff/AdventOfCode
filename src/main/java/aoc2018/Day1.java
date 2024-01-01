package aoc2018;

import common.DayBase;
import common.PuzzleInput;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Day1 extends DayBase<List<Integer>, Integer, Integer> {

    public Day1() {
        super();
    }

    public Day1(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<Integer> frequencyChanges = this.getInput(PuzzleInput::asIntList);

        return frequencyChanges.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<Integer> frequencyChanges = this.getInput(PuzzleInput::asIntList);

        int frequency = 0;
        int idx = 0;
        Set<Integer> reachedFrequencies = new HashSet<>();
        while (reachedFrequencies.add(frequency)) {
            frequency += frequencyChanges.get(idx);
            idx = (idx + 1) % frequencyChanges.size();
        }
        return frequency;
    }
}
