package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day9 extends DayBase<List<List<Integer>>, Integer, Integer> {

    public Day9() {
        super();
    }

    public Day9(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<List<Integer>> sensors = this.getInput(Day9::parseSensors);

        return sensors.stream()
                .mapToInt(Day9::extrapolate)
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<List<Integer>> sensors = this.getInput(Day9::parseSensors);

        return sensors.stream()
                .mapToInt(Day9::extrapolateBackward)
                .sum();
    }

    private static int extrapolate(List<Integer> sensor) {
        List<List<Integer>> sequences = computeSequences(sensor);

        int extrapolation = 0;
        for (int i = sequences.size() - 2; i >= 0 ; i--) {
            extrapolation += sequences.get(i).get(sequences.get(i).size() - 1);
        }

        return extrapolation;
    }

    private static int extrapolateBackward(List<Integer> sensor) {
        List<List<Integer>> sequences = computeSequences(sensor);

        int extrapolation = 0;
        for (int i = sequences.size() - 2; i >= 0 ; i--) {
            extrapolation = sequences.get(i).get(0) - extrapolation;
        }

        return extrapolation;
    }

    private static List<List<Integer>> computeSequences(List<Integer> sensor) {
        List<List<Integer>> sequences = new ArrayList<>();
        sequences.add(sensor);

        while (!sequences.get(sequences.size() - 1).stream().allMatch(n -> n == 0)) {
            List<Integer> seq = new ArrayList<>();
            for (int i = 0; i < sequences.get(sequences.size() - 1).size() - 1; i++) {
                seq.add(sequences.get(sequences.size() - 1).get(i + 1) - sequences.get(sequences.size() - 1).get(i));
            }
            sequences.add(seq);
        }

        return sequences;
    }


    private static List<List<Integer>> parseSensors(List<String> input) {
        return input.stream()
                .map(l-> Arrays.stream(l.split(" "))
                        .map(Integer::parseInt)
                        .toList())
                .toList();
    }
}
