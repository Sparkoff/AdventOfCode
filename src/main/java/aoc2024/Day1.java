package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day1 extends DayBase<Day1.Pairs, Integer, Integer> {

    public Day1() {
        super();
    }

    public Day1(List<String> input) {
        super(input);
    }

    record Pairs(List<Integer> left, List<Integer> right, int count) {
        public Pairs(List<Integer> left, List<Integer> right) {
            this(left, right, left.size());
        }

        Pairs sorted() {
            return new Pairs(
                    left.stream().sorted().toList(),
                    right.stream().sorted().toList(),
                    count
            );
        }

        WeightedPairs weights() {
            return new WeightedPairs(
                    left.stream()
                            .collect(Collectors.groupingBy(
                                    Function.identity(),
                                    Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                            )),
                    right.stream()
                            .collect(Collectors.groupingBy(
                                    Function.identity(),
                                    Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                            ))
            );
        }
    }
    record WeightedPairs(Map<Integer, Integer> left, Map<Integer, Integer> right) {}


    @Override
    public Integer firstStar() {
        Pairs pairs = this.getInput(Day1::parsePairs).sorted();

        return IntStream.range(0, pairs.count())
                .map(i -> Math.abs(pairs.left().get(i) - pairs.right().get(i)))
                .sum();
    }

    @Override
    public Integer secondStar() {
        WeightedPairs pairs = this.getInput(Day1::parsePairs).weights();

        return pairs.left().entrySet().stream()
                .mapToInt(e -> e.getKey() * pairs.right().getOrDefault(e.getKey(), 0) * e.getValue())
                .sum();
    }

    private static Pairs parsePairs(List<String> input) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        input.stream()
                .map(l -> l.split(" {3}"))
                .map(l -> Arrays.stream(l).map(Integer::parseInt).toList())
                .forEach(l -> {
                    left.add(l.get(0));
                    right.add(l.get(1));
                });

        return new Pairs(left, right);
    }
}
