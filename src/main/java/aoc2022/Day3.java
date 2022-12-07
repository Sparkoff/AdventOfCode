package aoc2022;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day3 extends DayBase<List<String>, Integer> {

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
        return null;
    }


    protected static String getItemsInBothSide(String items) {
        String left = items.substring(0, items.length() / 2);
        String right = items.substring(items.length() / 2);

        return Arrays.stream(left.split(""))
                .filter(right::contains)
                .findFirst()
                .orElseThrow();
    }

    private List<List<String>>

    protected static int getItemValue(String item) {
        return ALPHABET.indexOf(item) + 1;
    }

}
