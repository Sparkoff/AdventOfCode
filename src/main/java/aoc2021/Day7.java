package aoc2021;

import common.DayBase;
import common.PuzzleInput;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day7 extends DayBase<List<Integer>, Integer, Integer> {

    public Day7() {
        super();
    }

    public Day7(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstStar() {
        List<Integer> positions = this.getInput(PuzzleInput::asInlineIntListWithComma);

        int min = positions.stream()
                .min(Comparator.comparing(Integer::intValue))
                .orElseThrow();
        int max = positions.stream()
                .max(Comparator.comparing(Integer::intValue))
                .orElseThrow();

        return IntStream.rangeClosed(min, max)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), e -> positions))
                .entrySet()
                .stream()
                .map(e -> e.getValue().stream()
                        .map(p -> Math.abs(p - e.getKey()))
                        .reduce(0, Integer::sum))
                .min(Comparator.comparing(Integer::intValue))
                .orElseThrow();
    }

    @Override
    public Integer secondStar() {
        List<Integer> positions = this.getInput(PuzzleInput::asInlineIntListWithComma);

        int min = positions.stream()
                .min(Comparator.comparing(Integer::intValue))
                .orElseThrow();
        int max = positions.stream()
                .max(Comparator.comparing(Integer::intValue))
                .orElseThrow();

        return IntStream.rangeClosed(min, max)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), e -> positions))
                .entrySet()
                .stream()
                .map(e -> e.getValue().stream()
                        .map(p -> Day7.sumNaturalNumbers(Math.abs(p - e.getKey())))
                        .reduce(0, Integer::sum))
                .min(Comparator.comparing(Integer::intValue))
                .orElseThrow();
    }

    private static int sumNaturalNumbers(int n) {
        return n * (n + 1) / 2;
    }
}
