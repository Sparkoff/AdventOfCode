package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day12 extends DayBase<Day12.Pattern, Integer, Long> {

    public Day12() {
        super();
    }

    public Day12(List<String> input) {
        super(input);
    }

    record State(String pots, long index) {
        public State(String pots) {
            this(pots, 0);
        }
    }
    record Notes(State initialState, Map<String,String> rules) {
        public Notes(String initState, Map<String, String> rules) {
            this(new State(initState), rules);
        }
    }
    record Pattern(List<State> generations) {
        public int sumPotsAt(int t) {
            return (int) sumPotsAt((long) t);
        }
        public long sumPotsAt(long t) {
            State s;
            if (t < generations.size()) {
                s = generations.get((int) t);
            } else {
                s = slide(
                        generations.get(generations.size() - 1),
                        t - generations.size() + 1
                );
            }

            return IntStream.range(0, s.pots().length())
                    .mapToObj(i -> s.pots().charAt(i) == '#' ? i + s.index() : 0)
                    .mapToLong(p -> p)
                    .sum();
        }

        public State slide(State s, long genCount) {
            return new State(
                    s.pots(),
                    s.index() + genCount
            );
        }
    }


    @Override
    public Integer firstStar() {
        Pattern generationPattern = this.getInput(Day12::extractGenerationPattern);

        return generationPattern.sumPotsAt(20);
    }

    @Override
    public Long secondStar() {
        Pattern generationPattern = this.getInput(Day12::extractGenerationPattern);

        return generationPattern.sumPotsAt((long) 5E10);
    }

    private static Notes parseNotes(List<String> input) {
        String initState = input.get(0).split(": ")[1];

        Map<String,String> rules = input.subList(2, input.size()).stream()
                .map(l -> l.split(" => "))
                .collect(Collectors.toMap(l -> l[0], l -> l[1]));

        return new Notes(initState, rules);
    }
    private static State nextGeneration(State state, Notes notes) {
        long index = state.index() - 3;
        String current = "....." + state.pots() + ".....";
        StringBuilder next = new StringBuilder();

        for (int i = 0; i < current.length() - 4; i++) {
            next.append(notes.rules().getOrDefault(current.substring(i, i + 5), "."));
        }

        // trim starting and ending '.'
        while (next.charAt(0) == '.') {
            next.deleteCharAt(0);
            index++;
        }
        while (next.charAt(next.length() - 1) == '.') {
            next.deleteCharAt(next.length() - 1);
        }

        return new State(next.toString(), index);
    }
    private static Pattern extractGenerationPattern(List<String> input) {
        Notes notes = parseNotes(input);

        List<State> gens = new ArrayList<>();
        gens.add(notes.initialState());

        while (true) {
            State current = nextGeneration(gens.get(gens.size() - 1), notes);
            gens.add(current);

            // search for sliding pattern, like
            // .##.#..###
            // ...##.#.##
            // ....#..#.##
            // .....#..#.## <--
            // ......#..#.##
            if (current.pots().equals(gens.get(gens.size() - 2).pots())) {
                break;
            }
        }

        return new Pattern(gens);
    }
}
