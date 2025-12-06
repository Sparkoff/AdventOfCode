package aoc2025;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day6 extends DayBase<List<Day6.Problem>, Long, Long> {

    public Day6() {
        super();
    }

    public Day6(List<String> input) {
        super(input);
    }


    enum Operator { SUM, MULTIPLY }
    record Problem(List<Integer> horizontals, List<Integer> verticals, Operator operator) {
        public long calculateHorizontals() {
            return calculate(horizontals, operator);
        }
        public long calculateVerticals() {
            return calculate(verticals, operator);
        }
        public static long calculate(List<Integer> numbers, Operator operator) {
            return operator == Operator.SUM ?
                    numbers.stream().mapToLong(n -> (long) n).sum() :
                    numbers.stream().mapToLong(n -> (long) n).reduce(1, (a, b) -> a * b);
        }
    }


    @Override
    public Long firstStar() {
        List<Problem> problems = this.getInput(Day6::parseProblems);

        return problems.stream()
                .mapToLong(Problem::calculateHorizontals)
                .sum();
    }

    @Override
    public Long secondStar() {
        List<Problem> problems = this.getInput(Day6::parseProblems);

        return problems.stream()
                .mapToLong(Problem::calculateVerticals)
                .sum();
    }


    private static List<Problem> parseProblems(List<String> input) {
        List<String> worksheet = input.stream()
                .map(l -> l + " ")
                .collect(Collectors.toList());
        String worksheetOperators = worksheet.remove(worksheet.size() - 1);

        List<Problem> problems = new ArrayList<>();
        List<StringBuilder> numbers = IntStream.range(0, worksheet.size())
                .mapToObj(index -> new StringBuilder())
                .toList();
        Operator operator = Operator.SUM;
        for (int i = 0; i < worksheetOperators.length(); i++) {
            boolean complete = true;

            if (worksheetOperators.charAt(i) == '+') {
                operator = Operator.SUM;
            } else if (worksheetOperators.charAt(i) == '*') {
                operator = Operator.MULTIPLY;
            }

            for (int j = 0; j < worksheet.size(); j++) {
                char digit = worksheet.get(j).charAt(i);
                if (digit != ' ') {
                    complete = false;
                }
                numbers.get(j).append(digit);
            }

            if (complete) {
                List<Integer> horizontals = numbers.stream()
                        .map(StringBuilder::toString)
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .toList();

                List<StringBuilder> verticalNumbers = IntStream.range(0, numbers.get(0).length() - 1)
                        .mapToObj(index -> new StringBuilder())
                        .toList();
                for (StringBuilder number : numbers) {
                    for (int j = 0; j < numbers.get(0).length() - 1; j++) {
                        verticalNumbers.get(j).append(number.charAt(j));
                    }
                }
                List<Integer> verticals = verticalNumbers.stream()
                        .map(StringBuilder::toString)
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .toList();

                problems.add(new Problem(horizontals, verticals, operator));

                // reset
                numbers = IntStream.range(0, worksheet.size())
                        .mapToObj(index -> new StringBuilder())
                        .toList();
            }
        }

        return problems;
    }
}
