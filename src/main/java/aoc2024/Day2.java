package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class Day2 extends DayBase<List<List<Integer>>, Integer, Integer> {

    public Day2() {
        super();
    }

    public Day2(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<List<Integer>> reports = this.getInput(Day2::parseReports);

        return (int) reports.stream()
                .filter(this::isSafe)
                .count();
    }

    @Override
    public Integer secondStar() {
        List<List<Integer>> reports = this.getInput(Day2::parseReports);

        return (int) reports.stream()
                .filter(this::isSingleLevelSafe)
                .count();
    }

    private boolean isSafe(List<Integer> report) {
        List<Integer> diffs = new ArrayList<>();
        for (int i = 0; i < report.size() - 1; i++) {
            diffs.add(report.get(i) - report.get(i + 1));
        }

        if (diffs.stream().allMatch(d -> d > 0) || diffs.stream().allMatch(d -> d < 0)) {
            return diffs.stream().allMatch(d -> Math.abs(d) <= 3);
        }
        return false;
    }

    private boolean isSingleLevelSafe(List<Integer> report) {
        if (isSafe(report)) {
            return true;
        }

        int len = report.size();
        for (int i = 0; i < len; i++) {
            if (isSafe(
                    Stream.concat(
                            report.subList(0, i).stream(),
                            report.subList(i + 1, len).stream()
                    ).toList())
            ) {
                return true;
            }
        }

        return false;
    }


    private static List<List<Integer>> parseReports(List<String> input) {
        return input.stream()
                .map(l -> l.split(" "))
                .map(l -> Arrays.stream(l).map(Integer::parseInt).toList())
                .toList();
    }
}
