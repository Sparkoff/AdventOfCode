package aoc2023;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Day1 extends DayBase<List<String>, Integer, Integer> {

    public Day1() {
        super();
    }

    public Day1(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<String> calibration = this.getInput(PuzzleInput::asStringList);


        return calibration.stream()
                .mapToInt(l -> {
                    StringBuilder number = new StringBuilder();
                    for (String c : l.split("")) {
                        try {
                            number.append(Integer.parseInt(c));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    return Integer.parseInt(String.valueOf(number.charAt(0)) + String.valueOf(number.charAt(number.length() - 1)));
                })
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<String> calibration = this.getInput(PuzzleInput::asStringList);

        Pattern p = Pattern.compile("(?=(one|two|three|four|five|six|seven|eight|nine|\\d)).");

        return calibration.stream()
                .map(l -> {
                    List<String> s = new ArrayList<>();
                    Matcher m = p.matcher(l);
                    while (m.find()) {
                        s.add(m.group(1));
                    }
                    return s;
                })
                .map(l -> List.of(l.get(0), l.get(l.size() - 1)))
                .map(l -> l.stream()
                        .map(s -> switch (s) {
                            case "one" -> "1";
                            case "two" -> "2";
                            case "three" -> "3";
                            case "four" -> "4";
                            case "five" -> "5";
                            case "six" -> "6";
                            case "seven" -> "7";
                            case "eight" -> "8";
                            case "nine" -> "9";
                            default -> s;
                        })
                        .collect(Collectors.joining("")))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
