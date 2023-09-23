package aoc2021;

import common.DayBase;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day3 extends DayBase<List<Day3.ReportNumber>, Integer , Integer> {

    public Day3() {
        super();
    }

    public Day3(List<String> input) {
        super(input);
    }

    record ReportNumber(String line) {
        public int getBit(int index) {
            return Integer.parseInt(String.valueOf(this.line.charAt(index)));
        }
    }

    @Override
    public Integer firstStar() {
        List<ReportNumber> reportNumbers = this.getInput(Day3::getReportNumbers);

        String gamma = IntStream.range(0, reportNumbers.get(0).line.length())
                .mapToObj(i -> reportNumbers.stream()
                        .map(b -> b.getBit(i))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow()
                        .getKey()
                )
                .map(String::valueOf)
                .collect(Collectors.joining());

        String epsilon = IntStream.range(0, reportNumbers.get(0).line.length())
                .mapToObj(i -> reportNumbers.stream()
                        .map(b -> b.getBit(i))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .min(Map.Entry.comparingByValue())
                        .orElseThrow()
                        .getKey()
                )
                .map(String::valueOf)
                .collect(Collectors.joining());

        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
    }

    @Override
    public Integer secondStar() {
        List<ReportNumber> reportNumbers = this.getInput(Day3::getReportNumbers);

        Comparator<Map.Entry<Integer, List<ReportNumber>>> compareBySize = Comparator.comparing(e -> e.getValue().size());

        List<ReportNumber> remainingNumbers = reportNumbers;
        int index = 0;
        while (remainingNumbers.size() > 1) {
            int idx = index;
            remainingNumbers = remainingNumbers.stream()
                    .collect(Collectors.groupingBy(b -> b.getBit(idx), Collectors.toList()))
                    .entrySet()
                    .stream()
                    .max(compareBySize.thenComparing(Map.Entry::getKey))
                    .orElseThrow()
                    .getValue();
            index++;
        }
        String oxygen = remainingNumbers.get(0).line;

        remainingNumbers = reportNumbers;
        index = 0;
        while (remainingNumbers.size() > 1) {
            int idx = index;
            remainingNumbers = remainingNumbers.stream()
                    .collect(Collectors.groupingBy(b -> b.getBit(idx), Collectors.toList()))
                    .entrySet()
                    .stream()
                    .min(compareBySize.thenComparing(Map.Entry::getKey))
                    .orElseThrow()
                    .getValue();
            index++;
        }
        String co2 = remainingNumbers.get(0).line;

        return Integer.parseInt(oxygen, 2) * Integer.parseInt(co2, 2);
    }

    private static List<ReportNumber> getReportNumbers(List<String> input) {
        return input.stream()
                .map(ReportNumber::new)
                .toList();
    }
}
