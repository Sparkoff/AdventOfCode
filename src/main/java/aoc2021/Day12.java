package aoc2021;

import common.DayBase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Day12 extends DayBase<Map<Day12.Cave, List<Day12.Cave>>, Integer, Integer> {

    public Day12() {
        super();
    }

    public Day12(List<String> input) {
        super(input);
    }

    record Cave(String name, boolean isBig) {
        public Cave(String name) {
            this(name, name.matches("[A-Z]+"));
        }

        public boolean isStart() {
            return name.equals("start");
        }

        public boolean isEnd() {
            return name.equals("end");
        }
    }


    @Override
    public Integer firstStar() {
        Map<Cave, List<Cave>> caveSystem = this.getInput(Day12::parseCaveSystem);
        return countPaths(new Cave("start"), new HashSet<>(), caveSystem, false);
    }

    @Override
    public Integer secondStar() {
        Map<Cave, List<Cave>> caveSystem = this.getInput(Day12::parseCaveSystem);
        return countPaths(new Cave("start"), new HashSet<>(), caveSystem, true);
    }

    private int countPaths(Cave current, Set<Cave> visited, Map<Cave, List<Cave>> caveSystem, boolean canVisitSmallCaveTwice) {
        if (current.isEnd()) {
            return 1;
        }

        if (!current.isBig) {
            if (visited.contains(current)) {
                if (!canVisitSmallCaveTwice) return 0;
                canVisitSmallCaveTwice = false; // Use up the one-time allowance
            }
            visited.add(current);
        }

        int totalPaths = 0;
        for (Cave neighbor : caveSystem.get(current)) {
            if (neighbor.isStart()) continue;

            // A recursive call is valid if the neighbor is a big cave, or if it hasn't been visited,
            // or if we are still allowed to visit one small cave twice.
            if (neighbor.isBig || !visited.contains(neighbor) || canVisitSmallCaveTwice) {
                totalPaths += countPaths(neighbor, new HashSet<>(visited), caveSystem, canVisitSmallCaveTwice);
            }
        }

        return totalPaths;
    }

    private static Map<Cave, List<Cave>> parseCaveSystem(List<String> input) {
        List<Map.Entry<Cave, Cave>> connections = input.stream()
                .map(l -> Arrays.stream(l.split("-"))
                        .map(Cave::new)
                        .toList())
                .map(l -> Map.of(l.get(0), l.get(1), l.get(1), l.get(0)))
                .flatMap(m -> m.entrySet().stream())
                .toList();

        return connections.stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }
}