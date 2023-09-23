package aoc2017;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.List;


public class Day17 extends DayBase<Integer, Integer , Integer> {

    public Day17() {
        super();
    }

    public Day17(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstStar() {
        int stepPerInsert = this.getInput(PuzzleInput::asInt);

        List<Integer> circular = new ArrayList<>();
        circular.add(0);

        int idx = 0;
        for (int i = 1; i <= 2017; i++) {
            idx += stepPerInsert;
            while (idx >= circular.size()) {
                idx -= circular.size();
            }

            idx++;
            circular.add(idx, i);
        }

        return circular.get(idx + 1);
    }

    @Override
    public Integer secondStar() {
        int stepPerInsert = this.getInput(PuzzleInput::asInt);

        int idx = 1;
        int nextZero = 1;

        for (int size = 2; size <= 5E7; size++) {
            idx += stepPerInsert;
            while (idx >= size) {
                idx -= size;
            }

            idx++;
            if (idx == 1) {
                nextZero = size;
            }
        }

        return nextZero;
    }
}
