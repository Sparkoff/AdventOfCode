package aoc2017;

import common.DayBase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day4 extends DayBase<List<List<String>>, Integer> {

    public Day4() {
        super();
    }

    public Day4(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstStar() {
        List<List<String>> passphrases = this.getInput(Day4::parsePassphrases);

        return countNoneDuplicate(passphrases.stream());
    }

    @Override
    public Integer secondStar() {
        List<List<String>> passphrases = this.getInput(Day4::parsePassphrases);

        return countNoneDuplicate(passphrases.stream()
                .map(pass -> pass.stream()
                        .map(w -> Arrays.stream(w.split(""))
                                .sorted()
                                .collect(Collectors.joining()))
                        .toList()));
    }

    static int countNoneDuplicate(Stream<List<String>> passphrases) {
        return Math.toIntExact(passphrases
                .map(pass -> pass.stream()
                        .filter(s -> Collections.frequency(pass, s) > 1)
                        .toList())
                .filter(List::isEmpty)
                .count());
    }

    private static List<List<String>> parsePassphrases(List<String> input) {
        return input.stream()
                .map(pass -> Arrays.stream(pass.split("\\s+")).toList())
                .toList();
    }
}
