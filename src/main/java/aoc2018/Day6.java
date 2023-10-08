package aoc2018;

import common.DayBase;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day6 extends DayBase<List<Day6.Point>, Integer, Integer> {

    private int areaLimit = (int) 1E4;

    public Day6() {
        super();
    }

    public Day6(List<String> input) {
        super(input);
    }

    public Day6 testMode() {
        areaLimit = 32;
        return this;
    }

    record Point(int x, int y) {
        public int manhattan(Point other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y);
        }
    }
    record Grid(int xmin, int xmax, int ymin, int ymax) {
        public boolean onEdge(Point p) {
            return p.x() == xmin || p.x() == xmax || p.y() == ymin || p.y() == ymax;
        }
    }


    @Override
    public Integer firstStar() {
        List<Point> coordinates = this.getInput(Day6::parseCoordinates);

        Set<Integer> infiniteAreas = new HashSet<>();
        Map<Integer,Integer> areaSizes = new HashMap<>();

        Grid grid = extractGrid(coordinates);
        for (int x = grid.xmin(); x <= grid.xmax(); x++) {
            for (int y = grid.ymin(); y <= grid.ymax(); y++) {
                Point current = new Point(x, y);
                Map<Integer,List<Integer>> pointByDistance = IntStream.range(0, coordinates.size())
                        .mapToObj(i -> new AbstractMap.SimpleEntry<>(coordinates.get(i).manhattan(current), i))
                        .collect(Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(
                                        Map.Entry::getValue,
                                        Collectors.toList()
                                )
                        ));

                int minDist = Collections.min(pointByDistance.keySet());
                if (pointByDistance.get(minDist).size() == 1) {
                    int areaId = pointByDistance.get(minDist).get(0);
                    areaSizes.put(areaId, areaSizes.getOrDefault(areaId, 0) + 1);

                    if (grid.onEdge(current)) {
                        infiniteAreas.add(areaId);
                    }
                }
            }
        }

        return areaSizes.entrySet().stream()
                .filter(e -> !infiniteAreas.contains(e.getKey()))
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow()
                .getValue();
    }

    @Override
    public Integer secondStar() {
        List<Point> coordinates = this.getInput(Day6::parseCoordinates);

        int safeArea = 0;
        Grid grid = extractGrid(coordinates);
        for (int x = grid.xmin(); x <= grid.xmax(); x++) {
            for (int y = grid.ymin(); y <= grid.ymax(); y++) {
                Point current = new Point(x, y);
                int sumDist = coordinates.stream()
                        .mapToInt(c -> c.manhattan(current))
                        .sum();

                if (sumDist < areaLimit) {
                    safeArea++;
                }
            }
        }

        return safeArea;
    }

    private static Grid extractGrid(List<Point> coordinates) {
        List<Integer> xs = coordinates.stream()
                .map(Point::x)
                .toList();
        List<Integer> ys = coordinates.stream()
                .map(Point::y)
                .toList();
        return new Grid(
                Collections.min(xs),
                Collections.max(xs),
                Collections.min(ys),
                Collections.max(ys)
        );
    }

    private static List<Point> parseCoordinates(List<String> input) {
        return input.stream()
                .map(l -> l.split(", "))
                .map(l -> new Point(Integer.parseInt(l[0]), Integer.parseInt(l[1])))
                .toList();
    }
}
