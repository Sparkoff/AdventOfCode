package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Day22 extends DayBase<Day22.Cave, Integer, Integer> {

    public Day22() {
        super();
    }

    public Day22(List<String> input) {
        super(input);
    }

    enum RegionType {
        ROCKY(0),
        WET(1),
        NARROW(2);

        private final int value;

        RegionType(int value) {
            this.value = value;
        }

        public static RegionType byValue(int value) {
            return Arrays.stream(values())
                    .filter(t -> t.value == value)
                    .findFirst()
                    .orElseThrow();
        }

        public int getValue() {
            return value;
        }

        public boolean checkTool(Tool tool) {
            return switch (this) {
                case ROCKY -> tool == Tool.TORCH || tool == Tool.CLIMBING_GEAR;
                case WET -> tool == Tool.CLIMBING_GEAR || tool == Tool.NEITHER;
                case NARROW -> tool == Tool.TORCH || tool == Tool.NEITHER;
            };
        }
    }
    enum Tool { CLIMBING_GEAR, TORCH, NEITHER }

    record Point(int x, int y) {
        public static Point origin = new Point(0, 0);

        public Point(List<Integer> coordinates) {
            this(coordinates.get(0), coordinates.get(1));
        }

        public boolean lowerThan(Point other) {
            return x <= other.x && y <= other.y;
        }

        public List<Point> adjacentInclusive() {
            return List.of(
                    this,
                    new Point(x - 1, y),
                    new Point(x + 1, y),
                    new Point(x, y - 1),
                    new Point(x, y + 1)
            );
        }
    }

    record Step(Point point, Tool tool, int time) {}

    record Cave(Map<Point,RegionType> map, Point target) {}


    @Override
    public Integer firstStar() {
        Cave cave = this.getInput(Day22::buildCave);

        return cave.map().entrySet().stream()
                .filter(e -> e.getKey().lowerThan(cave.target()))
                .map(Map.Entry::getValue)
                .mapToInt(RegionType::getValue)
                .sum();
    }

    @Override
    public Integer secondStar() {
        Cave cave = this.getInput(Day22::buildCave);

        Map<Point,Map<Tool,Integer>> explored = new HashMap<>();
        List<Step> next = new ArrayList<>();
        next.add(new Step(Point.origin, Tool.TORCH, 0));

        while (!next.isEmpty()) {
            Step current = next.remove(0);

            if (!explored.containsKey(current.point())) {
                explored.put(
                        current.point(),
                        Arrays.stream(Tool.values())
                                .collect(Collectors.toMap(t -> t, t -> Integer.MAX_VALUE))
                );
            }

            if (current.time() < explored.get(current.point()).get(current.tool())) {
                // shorter path found for same tool, update time in exploration
                explored.get(current.point()).put(current.tool(), current.time());

                current.point().adjacentInclusive()
                        .stream()
                        .filter(p -> cave.map().containsKey(p))
                        .forEach(point -> {
                            if (point.equals(current.point())) {
                                // explore current location by switching tool
                                Arrays.stream(Tool.values())
                                        .filter(tool -> tool != current.tool() && cave.map().get(point).checkTool(tool))
                                        .forEach(tool -> next.add(new Step(point, tool, current.time() + 7)));
                            } else {
                                // explore adjacent locations with current tool
                                if (cave.map().get(point).checkTool(current.tool())) {
                                    next.add(new Step(point, current.tool(), current.time() + 1));
                                }
                            }
                        });
            }
        }

        return explored.get(cave.target()).get(Tool.TORCH);
    }

    private static Cave buildCave(List<String> input) {
        int depth = Integer.parseInt(input.get(0).split(" ")[1]);
        Point target = new Point(Arrays.stream(input.get(1).split(" ")[1].split(","))
                .map(Integer::parseInt)
                .toList());

        // set an offset in case the shortest path come outside the target coordinates
        // this path should not exceed the smallest coordinates (hope so!)
        int offset = Math.min(target.x(), target.y());

        Map<Point,RegionType> map = new HashMap<>();
        Map<Point,Integer> erosionLevels = new HashMap<>();

        for (int x = 0; x < target.x() + offset; x++) {
            for (int y = 0; y < target.y() + offset; y++) {
                Point p = new Point(x, y);

                int geologicalIndex;
                if (p.equals(target)) {
                    geologicalIndex = 0;
                } else if (y == 0) {
                    geologicalIndex = x * 16807;
                } else if (x == 0) {
                    geologicalIndex = y * 48271;
                } else {
                    geologicalIndex = erosionLevels.get(new Point(x - 1, y)) *
                            erosionLevels.get(new Point(x, y - 1));
                }

                int erosionLevel = (geologicalIndex + depth) % 20183;
                RegionType regionType = RegionType.byValue(erosionLevel % 3);

                erosionLevels.put(p, erosionLevel);
                map.put(p, regionType);
            }
        }

        return new Cave(map, target);
    }
}
