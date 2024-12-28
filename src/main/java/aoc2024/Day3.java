package aoc2024;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day3 extends DayBase<List<String>, Integer, Integer> {

    public Day3() {
        super();
    }

    public Day3(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<String> memory = this.getInput(PuzzleInput::asStringList);

        Pattern pattern = Pattern.compile("(mul\\(\\d+,\\d+\\))");
        return memory.stream()
                .map(pattern::matcher)
                .map(this::match)
                .flatMap(List::stream)
                .mapToInt(this::multiply)
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<String> memory = this.getInput(PuzzleInput::asStringList);

        Pattern pattern = Pattern.compile("(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))");
        List<String> commands = memory.stream()
                .map(pattern::matcher)
                .map(this::match)
                .flatMap(List::stream)
                .toList();

        List<String> muls = new ArrayList<>();
        boolean add = true;
        for (String command : commands) {
            if (command.equals("do()")) {
                add = true;
            } else if (command.equals("don't()")) {
                add = false;
            } else if (add) {
                muls.add(command);
            }
        }

        return muls.stream()
                .mapToInt(this::multiply)
                .sum();
    }

    private List<String> match(Matcher m) {
        List<String> groups = new ArrayList<>();
        while (m.find()) {
            groups.add(m.group());
        }
        return groups;
    }
    private int multiply(String command) {
        String[] values = command.substring(4, command.length() - 1).split(",");
        return Arrays.stream(values)
                .map(Integer::parseInt)
                .reduce(1, (a, c) -> a * c);
    }
}
