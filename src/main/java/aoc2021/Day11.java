package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day11 extends DayBase<List<Integer>, Integer> {

    public Day11() {
        super();
    }

    public Day11(List<String> input) {
        super(input);
    }

    record Step(List<Integer> energyLevels, int flashCount) {}

    @Override
    public Integer firstStar() {
        List<Integer> energyLevels = this.getInput(Day11::parseOctopusCluster);

        int flashCount = 0;
        for (int i = 0; i < 100; i++) {
            Step next = computeStep(energyLevels);

            flashCount += next.flashCount;
            energyLevels = next.energyLevels;
        }

        return flashCount;
    }

    @Override
    public Integer secondStar() {
        List<Integer> energyLevels = this.getInput(Day11::parseOctopusCluster);

        int step = 1;
        while (true) {
            Step next = computeStep(energyLevels);

            if (next.flashCount == 100) {
                return step;
            }

            energyLevels = next.energyLevels;
            step++;
        }
    }

    private static Step computeStep(List<Integer> cluster) {
        List<Integer> finalCluster = cluster.stream()
                .map(el -> el + 1)
                .collect(Collectors.toList());

        List<Integer> visited = new ArrayList<>();
        List<Integer> remains = IntStream.range(0, cluster.size())
                .filter(idx -> finalCluster.get(idx) > 9)
                .boxed()
                .collect(Collectors.toList());
        while (!remains.isEmpty()) {
            int next = remains.remove(0);
            if (visited.contains(next)) continue;

            List<Integer> adjacents = getAdjacentIndexes(next).stream()
                    .peek(idx -> finalCluster.set(idx, finalCluster.get(idx) + 1))
                    .filter(idx -> finalCluster.get(idx) > 9 && !visited.contains(idx))
                    .toList();

            remains.addAll(adjacents);
            visited.add(next);
        }

        AtomicInteger flashCount = new AtomicInteger();
        cluster = finalCluster.stream()
                .map(e -> {
                    if (e > 9) {
                        flashCount.getAndIncrement();
                        return 0;
                    }
                    return e;
                })
                .collect(Collectors.toList());
        return new Step(cluster, flashCount.get());
    }

    private static List<Integer> getAdjacentIndexes(int idx) {
        List<Integer> adjacentIndexes = new ArrayList<>();

        boolean onLeftEdge = idx % 10 == 0;
        boolean onRightEdge = idx % 10 == 9;
        boolean onUpEdge = idx / 10 == 0;
        boolean onDownEdge = idx / 10 == 9;

        if (!onLeftEdge) {
            adjacentIndexes.add(idx - 1);
        }
        if (!onRightEdge) {
            adjacentIndexes.add(idx + 1);
        }
        if (!onUpEdge) {
            adjacentIndexes.add(idx - 10);
        }
        if (!onDownEdge) {
            adjacentIndexes.add(idx + 10);
        }

        if (!onLeftEdge && !onUpEdge) {
            adjacentIndexes.add(idx - 11);
        }
        if (!onRightEdge && !onUpEdge) {
            adjacentIndexes.add(idx - 9);
        }
        if (!onLeftEdge && !onDownEdge) {
            adjacentIndexes.add(idx + 9);
        }
        if (!onRightEdge && !onDownEdge) {
            adjacentIndexes.add(idx + 11);
        }

        return adjacentIndexes;
    }

    private static List<Integer> parseOctopusCluster(List<String> input) {
        return input.stream()
                .map(l -> Arrays.stream(l.split(""))
                        .map(Integer::parseInt)
                        .toList())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}