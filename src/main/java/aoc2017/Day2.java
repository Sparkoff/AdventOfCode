package aoc2017;

import common.DayBase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Day2 extends DayBase<List<List<Integer>>, Integer, Integer> {

    public Day2() {
        super();
    }

    public Day2(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<List<Integer>> spreadsheet = this.getInput(Day2::parseSpreadsheet);

        return spreadsheet.stream()
                .mapToInt(row -> Collections.max(row) - Collections.min(row))
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<List<Integer>> spreadsheet = this.getInput(Day2::parseSpreadsheet);

        return spreadsheet.stream()
                .mapToInt(row -> {
                    for (int num1 : row) {
                        for (int num2 : row) {
                            int div = num1 / num2;
                            if (div != 1 && div * num2 == num1) {
                                return div;
                            }
                        }
                    }
                    return 0;
                })
                .sum();
    }


    private static List<List<Integer>> parseSpreadsheet(List<String> input) {
        return input.stream()
                .map(row -> Arrays.stream(row.split("\\s+"))
                        .map(Integer::parseInt)
                        .toList())
                .toList();
    }
}
