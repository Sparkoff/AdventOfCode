package aoc2023;

import common.DayBase;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Day25 extends DayBase<Map<String, List<String>>, Integer, Integer> {

    public Day25() {
        super();
    }

    public Day25(List<String> input) {
        super(input);
    }

    record Node(String left, String right) {}


    @Override
    public Integer firstStar() {
        Map<String, List<String>> graph = this.getInput(Day25::parseComponents);

        return productOfThreeCutComponentSizes(graph);
    }

    @Override
    public Integer secondStar() {
        return 2023;
    }

    private int productOfThreeCutComponentSizes(Map<String, List<String>> graph) {
        Set<String> group = new HashSet<>(graph.keySet());

        while (group.stream().mapToInt(v-> count(graph, group, v)).sum() != 3) {
            group.remove(
                    group.stream()
                            .max(Comparator.comparingInt(v-> count(graph, group, v)))
                            .orElse(null)
            );
        }

        long sizeOfGroup = group.size();
        long sizeOfNotGroup = graph.keySet().stream()
                .filter(v -> !group.contains(v))
                .count();

        return (int) (sizeOfGroup * sizeOfNotGroup);
    }

    private int count(Map<String, List<String>> graph, Set<String> group, String vertex) {
        return (int) graph.getOrDefault(vertex, List.of()).stream()
                .filter(s -> !group.contains(s))
                .count();
    }


    private static Map<String, List<String>> parseComponents(List<String> input) {
        return input.stream()
                .map(l -> l.split(": "))
                .map(l -> Arrays.stream(l[1].split(" "))
                        .map(r -> List.of(new Node(l[0], r), new Node(r, l[0])))
                        .flatMap(List::stream)
                        .toList()
                )
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Node::left, Collectors.mapping(Node::right, Collectors.toList())));
    }
}
