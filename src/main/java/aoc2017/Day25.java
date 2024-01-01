package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Day25 extends DayBase<Day25.Blueprint, Integer, Integer> {

    public Day25() {
        super();
    }

    public Day25(List<String> input) {
        super(input);
    }

    record Blueprint(String initState, int steps, List<State> states) {
        public State getState(String label) {
            return states.stream()
                    .filter(s -> s.label().equals(label))
                    .findFirst()
                    .orElseThrow();
        }
    }
    record State(String label, List<Instruction> instructions) {
        public Instruction getInstruction(int currentValue) {
            return instructions.stream()
                    .filter(i -> i.current() == currentValue)
                    .findFirst()
                    .orElseThrow();
        }
    }
    record Instruction(int current, int toWrite, int toMove, String nextState) {}

    @Override
    public Integer firstStar() {
        Blueprint turingBlueprint = this.getInput(Day25::parseTuringBlueprint);

        int currentIdx = 0;
        String currentState = turingBlueprint.initState();
        Set<Integer> ones = new HashSet<>();

        for (int i = 0; i < turingBlueprint.steps(); i++) {
            Instruction instruction = turingBlueprint.getState(currentState)
                    .getInstruction(ones.contains(currentIdx) ? 1 : 0);

            if (instruction.toWrite() == 1) {
                ones.add(currentIdx);
            } else {
                ones.remove(currentIdx);
            }

            currentIdx += instruction.toMove();
            currentState = instruction.nextState();
        }

        return ones.size();
    }

    @Override
    public Integer secondStar() {
        return 2017;
    }

    private static Blueprint parseTuringBlueprint(List<String> input) {
        input = new ArrayList<>(input);
        String initState = extract(input.remove(0));
        int steps = extractAsInt(input.remove(0), -2);

        List<State> states = new ArrayList<>();
        while (!input.isEmpty()) {
            input.remove(0);
            states.add(new State(
                    extract(input.remove(0)),
                    List.of(
                            new Instruction(
                                    extractAsInt(input.remove(0)),
                                    extractAsInt(input.remove(0)),
                                    extract(input.remove(0)).equals("right") ? 1 : -1,
                                    extract(input.remove(0))
                            ),
                            new Instruction(
                                    extractAsInt(input.remove(0)),
                                    extractAsInt(input.remove(0)),
                                    extract(input.remove(0)).equals("right") ? 1 : -1,
                                    extract(input.remove(0))
                            )
                    )
            ));
        }

        return new Blueprint(initState, steps, states);
    }
    private static String extract(String line, int index) {
        line = line.substring(0, line.length() - 1);
        String[] words = line.split(" ");
        return words[words.length + index];
    }
    private static String extract(String line) {
        return extract(line, -1);
    }
    private static int extractAsInt(String line, int index) {
        return Integer.parseInt(extract(line, index));
    }
    private static int extractAsInt(String line) {
        return extractAsInt(line, -1);
    }
}
