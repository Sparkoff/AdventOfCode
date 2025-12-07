package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Day25 extends DayBase<Day25.Blueprint, Integer, Integer> {

    public Day25() {
        super();
    }

    public Day25(List<String> input) {
        super(input);
    }

    record Blueprint(String initState, int steps, Map<String, State> states) {}
    record State(String label, Instruction[] instructions) {
        public Instruction getInstruction(int currentValue) {
            return instructions[currentValue];
        }
    }
    record Instruction(int current, int toWrite, int toMove, String nextState) {}

    @Override
    public Integer firstStar() {
        Blueprint turingBlueprint = this.getInput(Day25::parseTuringBlueprint);

        int cursor = 0;
        State currentState = turingBlueprint.states().get(turingBlueprint.initState());
        HashSet<Integer> tape = new HashSet<>();

        for (int i = 0; i < turingBlueprint.steps(); i++) {
            Instruction instruction = currentState.getInstruction(tape.contains(cursor) ? 1 : 0);

            if (instruction.toWrite() == 1) {
                tape.add(cursor);
            } else {
                tape.remove(cursor);
            }

            cursor += instruction.toMove();
            currentState = turingBlueprint.states().get(instruction.nextState());
        }

        return tape.size();
    }

    @Override
    public Integer secondStar() {
        return 2017;
    }


    private static Blueprint parseTuringBlueprint(List<String> input) {
        input = new ArrayList<>(input);
        String initState = extract(input.remove(0));
        int steps = extractAsInt(input.remove(0), -2);

        List<State> stateList = new ArrayList<>();
        while (!input.isEmpty()) {
            input.remove(0);
            stateList.add(new State(
                    extract(input.remove(0)),
                    new Instruction[]{
                            parseInstruction(input), // Instruction for value 0
                            parseInstruction(input)  // Instruction for value 1
                    }
            ));
        }

        Map<String, State> states = stateList.stream()
                .collect(Collectors.toMap(State::label, Function.identity()));

        return new Blueprint(initState, steps, states);
    }

    private static Instruction parseInstruction(List<String> input) {
        return new Instruction(
                extractAsInt(input.remove(0)),
                extractAsInt(input.remove(0)),
                extract(input.remove(0)).equals("right") ? 1 : -1,
                extract(input.remove(0))
        );
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
