package aoc2021;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.List;

public class Day1 extends DayBase<List<Integer>, Integer , Integer> {

    public Day1() {
        super();
    }

    public Day1(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstStar() {
        return computeDepthIncrease(this.getInput(PuzzleInput::asIntList));
    }

    @Override
    public Integer secondStar() {
        return computeDepthIncrease(groupByWindow(this.getInput(PuzzleInput::asIntList)));
    }

    private List<Integer> groupByWindow(List<Integer> depths) {
        List<Integer> slidingDepths = new ArrayList<>();
        for (int i = 0; i < depths.size() - 2; i++) {
            int widowDepth = 0;
            for (int j = 0; j < 3; j++) {
                widowDepth += depths.get(i + j);
            }
            slidingDepths.add(widowDepth);
        }
        return slidingDepths;
    }

    private int computeDepthIncrease(List<Integer> depths) {
        int increasedCount = 0;
        for (int i = 1; i < depths.size(); i++) {
            if (depths.get(i) > depths.get(i - 1)) {
                increasedCount++;
            }
        }
        return increasedCount;
    }
}
