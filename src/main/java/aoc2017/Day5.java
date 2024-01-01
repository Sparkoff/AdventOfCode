package aoc2017;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.List;


public class Day5 extends DayBase<List<Integer>, Integer, Integer> {

    public Day5() {
        super();
    }

    public Day5(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstStar() {
        List<Integer> jumpOffsets = this.getInput(PuzzleInput::asIntList);

        return run(jumpOffsets, true);
    }

    @Override
    public Integer secondStar() {
        List<Integer> jumpOffsets = this.getInput(PuzzleInput::asIntList);

        return run(jumpOffsets, false);
    }

    static int run(List<Integer> jumpOffsets, boolean straightForward) {
        jumpOffsets = new ArrayList<>(jumpOffsets);

        int current = 0;
        int step = 0;

        while (current >= 0 && current < jumpOffsets.size()) {
            step++;

            int jump = jumpOffsets.get(current);
            int offset = (!straightForward && jump >= 3) ? -1 : 1;
            jumpOffsets.set(current, jump + offset);

            current += jump;
        }
        return step;
    }
}
