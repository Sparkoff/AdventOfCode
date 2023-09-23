package aoc2017;

import common.DayBase;
import common.PuzzleInput;

import java.util.List;
import java.util.ListIterator;


public class Day9 extends DayBase<String, Integer , Integer> {

    public Day9() {
        super();
    }

    public Day9(List<String> input) {
        super(input);
    }

    record StreamGarbage(int groupScore, int garbageScore) {}

    @Override
    public Integer firstStar() {
        String stream = this.getInput(PuzzleInput::asString);

        return parseStream(stream).groupScore();
    }

    @Override
    public Integer secondStar() {
        String stream = this.getInput(PuzzleInput::asString);

        return parseStream(stream).garbageScore();
    }

    private StreamGarbage parseStream(String stream) {
        int groupScore = 0;
        int currentGroupIdx = 0;
        int garbageScore = 0;

        ListIterator<String> it = List.of(stream.split("")).listIterator();
        while (it.hasNext()) {
            String c = it.next();

            switch (c) {
                case "{" -> {
                    currentGroupIdx++;
                    groupScore += currentGroupIdx;
                }
                case "}" -> currentGroupIdx--;
                case "<" -> {
                    while (it.hasNext()) {
                        c = it.next();
                        if (c.equals("!")) {
                            // ignore next char
                            it.next();
                        } else if (c.equals(">")) {
                            break;
                        } else {
                            garbageScore++;
                        }
                    }
                }
            }
        }

        return new StreamGarbage(groupScore, garbageScore);
    }
}
