package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Day8 extends DayBase<List<Day8.SevenSegment>, Integer , Integer> {

    public Day8() {
        super();
    }

    public Day8(List<String> input) {
        super(input);
    }

    record SevenSegment(List<String> patterns, List<String> digits, List<String> dictionary) {
        public SevenSegment(List<String> patterns, List<String> value) {
            this(patterns, value, new ArrayList<>());
        }

        public int getValue() {
            return Integer.parseInt(digits.stream()
                    .map(dictionary::indexOf)
                    .map(String::valueOf)
                    .collect(Collectors.joining()));
        }
    }


    @Override
    public Integer firstStar() {
        List<SevenSegment> segs = this.getInput(Day8::parse7Segs);

        return Math.toIntExact(segs.stream()
                .map(s -> s.digits)
                .flatMap(List::stream)
                .filter(d -> List.of(2, 3, 4, 7).contains(d.length()))
                .count());
    }

    @Override
    public Integer secondStar() {
        List<SevenSegment> segs = this.getInput(Day8::parse7Segs);

        return Math.toIntExact(segs.stream()
                .map(s -> new SevenSegment(s.patterns, s.digits, Day8.computeDictionary(s.patterns)))
                .mapToInt(SevenSegment::getValue)
                .sum());
    }

    private static List<String> computeDictionary(List<String> patterns) {
        List<String> dictionary = new ArrayList<>();

        Map<Integer, List<String>> digitSize = patterns.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.toList()));

        dictionary.add(digitSize.get(2).get(0)); // 1
        dictionary.add(digitSize.get(4).get(0)); // 4
        dictionary.add(digitSize.get(3).get(0)); // 7
        dictionary.add(digitSize.get(7).get(0)); // 8

        // 6 seg digits
        dictionary.add(digitSize.get(6).stream()
                .filter(d -> containsAllChar(d, dictionary.get(1)))
                .toList()
                .get(0)); // 9
        dictionary.add(0, digitSize.get(6).stream()
                .filter(d -> containsAllChar(d, dictionary.get(2)) && !d.equals(dictionary.get(4)))
                .toList()
                .get(0)); // 0
        dictionary.add(3, digitSize.get(6).stream()
                .filter(d -> !d.equals(dictionary.get(0)) && !d.equals(dictionary.get(5)))
                .toList()
                .get(0)); // 6

        // 5 seg digits
        dictionary.add(2, digitSize.get(5).stream()
                .filter(d -> containsAllChar(d, dictionary.get(1)))
                .toList()
                .get(0)); // 3
        dictionary.add(4, digitSize.get(5).stream()
                .filter(d -> containsAllChar(dictionary.get(7), d) && !d.equals(dictionary.get(2)))
                .toList()
                .get(0)); // 5
        dictionary.add(2, digitSize.get(5).stream()
                .filter(d -> !d.equals(dictionary.get(2)) && !d.equals(dictionary.get(4)))
                .toList()
                .get(0)); // 2

        return dictionary;
    }

    private static boolean containsAllChar(String a, String b) {
        return new HashSet<>(Arrays.stream(a.split("")).toList())
                .containsAll(Arrays.stream(b.split("")).toList());
    }

    private static List<SevenSegment> parse7Segs(List<String> input) {
        return input.stream()
                .map(l -> Arrays.stream(l.split( " \\| "))
                        .map(d -> Arrays.stream(d.split(" "))
                                .map(w -> Arrays.stream(w.split(""))
                                        .sorted(Comparator.naturalOrder())
                                        .collect(Collectors.joining()))
                                .toList())
                        .toList())
                .map(l -> new SevenSegment(l.get(0), l.get(1)))
                .toList();
    }
}