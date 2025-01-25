package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class Day7 extends DayBase<List<Day7.Equation>, Long, Long> {

    public Day7() {
        super();
    }

    public Day7(List<String> input) {
        super(input);
    }

    record Equation(long result, List<Long> operands) {}


    @Override
    public Long firstStar() {
        List<Equation> equations = this.getInput(Day7::parseEquations);

        return equations.stream()
                .filter(e -> getResults(e.operands(), false).contains(e.result()))
                .mapToLong(Equation::result)
                .sum();
    }

    @Override
    public Long secondStar() {
        List<Equation> equations = this.getInput(Day7::parseEquations);

        return equations.stream()
                .filter(e -> getResults(e.operands(), true).contains(e.result()))
                .mapToLong(Equation::result)
                .sum();
    }

    private List<Long> getResults(List<Long> operands, boolean useCombine) {
        operands = new ArrayList<>(operands);

        long first = operands.remove(0);
        return useCombine ? solveCombine(first, operands) : solve(first, operands);
    }

    private List<Long> solve(Long currentResult, List<Long> operands) {
        if (operands.isEmpty()) {
            return List.of(currentResult);
        }

        long next = operands.remove(0);
        return Stream
                .concat(
                        solve(currentResult + next, new ArrayList<>(operands)).stream(),
                        solve(currentResult * next, new ArrayList<>(operands)).stream()
                )
                .toList();
    }
    private List<Long> solveCombine(Long currentResult, List<Long> operands) {
        if (operands.isEmpty()) {
            return List.of(currentResult);
        }

        long next = operands.remove(0);
        return Stream
                .of(
                        solveCombine(currentResult + next, new ArrayList<>(operands)),
                        solveCombine(currentResult * next, new ArrayList<>(operands)),
                        solveCombine(combine(currentResult, next), new ArrayList<>(operands))
                )
                .flatMap(List::stream)
                .toList();
    }
    private Long combine(long a, long b) {
        return Long.parseLong(String.valueOf(a) + b);
    }


    private static List<Equation> parseEquations(List<String> input) {
        return input.stream()
                .map(l -> l.split(": "))
                .map(l -> new Equation(
                        Long.parseLong(l[0]),
                        Arrays.stream(l[1].split(" "))
                                .map(Long::parseLong)
                                .toList()
                ))
                .toList();
    }
}
