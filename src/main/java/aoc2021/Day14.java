package aoc2021;

import common.DayBase;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Day14 extends DayBase<Day14.PolymerFormula, Long> {

    public Day14() {
        super();
    }

    public Day14(List<String> input) {
        super(input);
    }

    record PolymerFormula(String origin, Map<String, Long> template, Map<String, String> rules) {}


    @Override
    public Long firstStar() {
        PolymerFormula formula = this.getInput(Day14::parsePolymer);

        Map<String, Long> polymer = formula.template;
        for (int i = 0; i < 10; i++) {
            polymer = makePolymer(polymer, formula.rules);
        }
        return getPolymerResult(polymer, formula.origin);
    }

    @Override
    public Long secondStar() {
        PolymerFormula formula = this.getInput(Day14::parsePolymer);

        Map<String, Long> polymer = formula.template;
        for (int i = 0; i < 40; i++) {
            polymer = makePolymer(polymer, formula.rules);
        }
        return getPolymerResult(polymer, formula.origin);
    }

    private static Long getPolymerResult(Map<String, Long> polymer, String template) {
        Map<String, Long> stat = new HashMap<>();

        for (String pair : polymer.keySet()) {
            String[] element = pair.split("");

            stat.put(element[0], polymer.get(pair) + stat.getOrDefault(element[0], 0L));
            stat.put(element[1], polymer.get(pair) + stat.getOrDefault(element[1], 0L));
        }

        stat = stat.entrySet()
                .stream()
                .map(e -> {
                    long value = e.getValue() / 2L;
                    if (template.startsWith(e.getKey())) value++;
                    if (template.endsWith(e.getKey())) value++;
                    return Map.of(e.getKey(), value);
                })
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        long max = stat.values().stream()
                .max(Comparator.naturalOrder())
                .orElseThrow();
        long min = stat.values().stream()
                .min(Comparator.naturalOrder())
                .orElseThrow();

        return max - min;
    }

    private static Map<String, Long> makePolymer(Map<String, Long> polymer, Map<String, String> rules) {
        Map<String, Long> next = new HashMap<>();

        for (String pair : polymer.keySet()) {
            String[] oldElement = pair.split("");
            String newElement = rules.get(pair);
            List<String> newPairs = List.of(oldElement[0] + newElement, newElement + oldElement[1]);
            next.put(newPairs.get(0), polymer.get(pair) + next.getOrDefault(newPairs.get(0), 0L));
            next.put(newPairs.get(1), polymer.get(pair) + next.getOrDefault(newPairs.get(1), 0L));
        }

        return next;
    }

    private static PolymerFormula parsePolymer(List<String> input) {
        String origin = "";
        Map<String, Long> template = new HashMap<>();
        Map<String, String> rules = new HashMap<>();

        for (String line : input) {
            if (line.isEmpty()) continue;

            if (line.contains("->")) {
                List<String> p = Arrays.stream(line.split(" -> ")).toList();
                rules.put(p.get(0), p.get(1));
            } else {
                for (int i = 0; i < line.length() - 1; i++) {
                    String pair = line.substring(i, i + 2);
                    template.put(pair, 1 + template.getOrDefault(pair, 0L));
                }
                origin = line;
            }
        }

        return new PolymerFormula(origin, template, rules);
    }
}