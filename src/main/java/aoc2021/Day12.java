package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day12 extends DayBase<Map<Day12.Cave, List<Day12.Cave>>, Integer> {

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

    enum PathMode {
        SINGLE, TWICE
    }

    @Override
    public Integer firstStar() {
        Map<Cave, List<Cave>> caveSystem = this.getInput(Day12::parseCaveSystem);

        return exploreCave(caveSystem, PathMode.SINGLE).size();
    }

    @Override
    public Integer secondStar() {
        Map<Cave, List<Cave>> caveSystem = this.getInput(Day12::parseCaveSystem);

        return exploreCave(caveSystem, PathMode.TWICE).size();
    }

    private List<List<Cave>> exploreCave(Map<Cave, List<Cave>> caveSystem, PathMode mode) {
        List<List<Cave>> paths = new ArrayList<>();

        List<List<Cave>> inProgress = new ArrayList<>();
        inProgress.add(List.of(new Cave("start")));

        while (inProgress.size() > 0) {
            List<Cave> next = inProgress.remove(0);

            List<List<Cave>> nextPossibilities = nextStep(next, caveSystem, mode);

            if (nextPossibilities != null) {
                inProgress.addAll(nextPossibilities.stream()
                        .filter(p -> !p.get(p.size() - 1).isEnd())
                        .toList());
                paths.addAll(nextPossibilities.stream()
                        .filter(p -> p.get(p.size() - 1).isEnd())
                        .toList());
            }
        }

        return paths;
    }

    private List<List<Cave>> nextStep(List<Cave> path, Map<Cave, List<Cave>> caveSystem, PathMode mode) {
        Cave lastCave = path.get(path.size() - 1);

        boolean noDuplicates = path.stream()
                .filter(p -> !p.isBig)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .noneMatch(p -> p.getValue() > 1);

        List<Cave> nextCaves = caveSystem.get(lastCave).stream()
                .filter(c -> !c.isStart() && (c.isEnd() || c.isBig || (mode == PathMode.SINGLE ?
                        !path.contains(c) :
                        !path.contains(c) || noDuplicates)))
                .toList();

        if (nextCaves.size() == 0) return null;

        return nextCaves.stream()
                .map(c -> Stream.concat(path.stream(), Stream.of(c)).toList())
                .toList();
    }

    private static Map<Cave, List<Cave>> parseCaveSystem(List<String> input) {
        return input.stream()
                .map(l -> Arrays.stream(l.split("-"))
                        .map(Cave::new)
                        .toList())
                .map(l -> Map.of(l.get(0), l.get(1), l.get(1), l.get(0)))
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }
}