package aoc2022;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day3 extends DayBase<List<String>, Integer, Integer> {

    private static final List<String> ALPHABET = List.of(
            ("abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz".toUpperCase()).split("")
    );

    public Day3() {
        super();
    }

    public Day3(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<String> bags = this.getInput(PuzzleInput::asStringList);
        return bags.stream()
                .map(Day3::getItemsInBothSide)
                .map(Day3::getItemValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<String> bags = this.getInput(PuzzleInput::asStringList);

        return IntStream.range(0, bags.size())
                .boxed()
                .collect(Collectors.groupingBy(idx -> idx / 3))
                .values().stream()
                .map(idx -> idx.stream().map(bags::get))
                .flatMap(group -> group
                        .flatMap(b -> Arrays.stream(b.split("")).distinct())
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .filter(e -> e.getValue() == 3)
                        .map(Map.Entry::getKey))
                .map(Day3::getItemValue)
                .mapToInt(Integer::intValue)
                .sum();
    }


    protected static String getItemsInBothSide(String items) {
        String left = items.substring(0, items.length() / 2);
        String right = items.substring(items.length() / 2);

        return Arrays.stream(left.split(""))
                .filter(right::contains)
                .findFirst()
                .orElseThrow();
    }

    //private List<List<String>>

    protected static int getItemValue(String item) {
        return ALPHABET.indexOf(item) + 1;
    }

}
