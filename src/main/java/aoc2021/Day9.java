package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;


public class Day9 extends DayBase<Day9.Heatmap, Integer> {

    public Day9() {
        super();
    }

    public Day9(List<String> input) {
        super(input);
    }

    record Heatmap(List<Integer> map, int width, int height) {}

    @Override
    public Integer firstStar() {
        Heatmap heatmap = this.getInput(Day9::parseHeatmap);

        return IntStream.range(0, heatmap.map.size())
                .boxed()
                .filter(i -> isLowPoint(i, heatmap))
                .mapToInt(i -> heatmap.map.get(i) + 1)
                .sum();
    }

    @Override
    public Integer secondStar() {
        Heatmap heatmap = this.getInput(Day9::parseHeatmap);

        return IntStream.range(0, heatmap.map.size())
                .boxed()
                .filter(i -> isLowPoint(i, heatmap))
                .map(i -> exploreBasin(i, heatmap))
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1, (p, i) -> p * i);
    }

    private static boolean isLowPoint(int index, Heatmap heatmap) {
        return getAdjacentIndexes(index, heatmap).stream()
                .allMatch(i -> heatmap.map.get(i) > heatmap.map.get(index));
    }

    private static int exploreBasin(int lowPoint, Heatmap heatmap) {
        List<Integer> visitedIndexes = new ArrayList<>();
        List<Integer> remains = new ArrayList<>();
        remains.add(lowPoint);

        while (remains.size() > 0) {
            int next = remains.remove(0);

            if (visitedIndexes.contains(next)) continue;

            List<Integer> adjacents = getAdjacentIndexes(next, heatmap).stream()
                    .filter(i -> heatmap.map.get(i) != 9 && !visitedIndexes.contains(i))
                    .toList();

            remains.addAll(adjacents);
            visitedIndexes.add(next);
        }

        return visitedIndexes.size();
    }

    private static List<Integer> getAdjacentIndexes(int index, Heatmap heatmap) {
        List<Integer> adjacentIndexes = new ArrayList<>();

        // left point : check current point is not on left edge
        if (index % heatmap.width > 0) {
            adjacentIndexes.add(index - 1);
        }
        // right point : check current point is not on right edge
        if (index % heatmap.width < heatmap.width - 1) {
            adjacentIndexes.add(index + 1);
        }

        // up point : check current point is not on up edge
        if ( index / heatmap.width > 0) {
            adjacentIndexes.add(index - heatmap.width);
        }
        // down point : check current point is not on down edge
        if ( index / heatmap.width < heatmap.height - 1) {
            adjacentIndexes.add(index + heatmap.width);
        }

        return adjacentIndexes;
    }

    private static Heatmap parseHeatmap(List<String> input) {
        int width = input.get(0).length();
        int height = input.size();

        List<Integer> map = input.stream()
                .map(l -> Arrays.stream(l.split(""))
                        .map(Integer::parseInt)
                        .toList())
                .flatMap(List::stream)
                .toList();

        return new Heatmap(map, width, height);
    }
}