package aoc2017;

import common.DayBase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Day13 extends DayBase<Map<Integer, Integer>, Integer> {

    public Day13() {
        super();
    }

    public Day13(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstStar() {
        Map<Integer, Integer> firewall = this.getInput(Day13::parseFirewall);

        return 0;
    }

    @Override
    public Integer secondStar() {
        Map<Integer, Integer> firewall = this.getInput(Day13::parseFirewall);

        return 0;

    }

    private static Map<Integer, Integer> parseFirewall(List<String> input) {
        return input.stream()
                .map(l -> l.split(": "))
                .collect(Collectors.toMap(l -> Integer.parseInt(l[0]), l -> Integer.parseInt(l[1])));
    }
}
