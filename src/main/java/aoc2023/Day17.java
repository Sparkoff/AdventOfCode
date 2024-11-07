package aoc2023;

import common.DayBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;


public class Day17 extends DayBase<Day17.HeatMap, Integer, Integer> {

    public Day17() {
        super();
    }

    public Day17(List<String> input) {
        super(input);
    }

    record HeatMap(List<String> map, int width, int height) {
        public int getHeat(int x, int y) {
            return Integer.parseInt(String.valueOf(map.get(y).charAt(x)));
        }

        public boolean exist(int x, int y) {
            return x >= 0 && y >= 0 && x < width && y < height;
        }
    }

    enum Type { VERTICAL, HORIZONTAL }
    record Location(Type type, int x, int y) {
        public boolean match(int x, int y) {
            return this.x == x && this.y == y;
        }
    }
    static class Node {
        private final Map<Location, Integer> neighbors = new HashMap<>();
        private int heat = Integer.MAX_VALUE;

        private void addNeighbor(Location location, Integer pathHeat) {
            neighbors.put(location, pathHeat);
        }

        public Set<Location> getNeighborLocations() {
            return neighbors.keySet();
        }
        public Map<Location, Integer> getNeighbors() {
            return neighbors;
        }
        public int getNeighborHeat(Location location) {
            return neighbors.get(location);
        }

        public int getHeat() {
            return heat;
        }
        public void setHeat(int heat) {
            this.heat = heat;
        }
    }
    static class HeatGraph {
        private final Map<Location, Node> graph = new HashMap<>();
        private final int width;
        private final int height;
        private final int moveMin;
        private final int moveMax;
        private int result = Integer.MAX_VALUE;

        public HeatGraph(HeatMap heatMap, int moveMin, int moveMax) {
            this.width = heatMap.width();
            this.height = heatMap.height();

            this.moveMin = moveMin;
            this.moveMax = moveMax;

            initGraph(heatMap);
        }

        private void initGraph(HeatMap heatMap) {
            for (int y = 0; y < heatMap.height(); y++) {
                for (int x = 0; x < heatMap.width(); x++) {
                    Location vertical = new Location(Type.VERTICAL, x, y);
                    Location horizontal = new Location(Type.HORIZONTAL, x, y);

                    graph.put(vertical, new Node());
                    graph.put(horizontal, new Node());

                    int finalX = x;
                    int finalY = y;
                    for (int i = moveMin; i <= moveMax; i++) {
                        if (heatMap.exist(x, y + i)) {
                            graph.get(vertical).addNeighbor(
                                    new Location(Type.HORIZONTAL, x, y + i),
                                    IntStream.range(1, i + 1)
                                            .map(j -> heatMap.getHeat(finalX, finalY + j))
                                            .sum()
                            );
                        }
                        if (heatMap.exist(x, y - i)) {
                            graph.get(vertical).addNeighbor(
                                    new Location(Type.HORIZONTAL, x, y - i),
                                    IntStream.range(1, i + 1)
                                            .map(j -> heatMap.getHeat(finalX, finalY - j))
                                            .sum()
                            );
                        }
                        if (heatMap.exist(x + i, y)) {
                            graph.get(horizontal).addNeighbor(
                                    new Location(Type.VERTICAL, x + i, y),
                                    IntStream.range(1, i + 1)
                                            .map(j -> heatMap.getHeat(finalX + j, finalY))
                                            .sum()
                            );
                        }
                        if (heatMap.exist(x - i, y)) {
                            graph.get(horizontal).addNeighbor(
                                    new Location(Type.VERTICAL, x - i, y),
                                    IntStream.range(1, i + 1)
                                            .map(j -> heatMap.getHeat(finalX - j, finalY))
                                            .sum()
                            );
                        }
                    }
                }
            }
        }

        public int run() {
            Map<Location, Integer> startingNeighbors = new HashMap<>();
            startingNeighbors.putAll(graph.get(new Location(Type.VERTICAL, 0, 0)).getNeighbors());
            startingNeighbors.putAll(graph.get(new Location(Type.HORIZONTAL, 0, 0)).getNeighbors());

            for (Map.Entry<Location, Integer> startingNeighbor : startingNeighbors.entrySet()) {
                walk(startingNeighbor.getKey(), startingNeighbor.getValue());
            }

            return this.result;
        }

        private void walk(Location location, int heat) {
            if (heat >= Math.min(graph.get(location).getHeat(), result)) {
                return;
            }

            if (location.match(width - 1,  height - 1)) {
                result = heat;
                return;
            }

            graph.get(location).setHeat(heat);
            for (Location neighbor : graph.get(location).getNeighborLocations()) {
                walk(neighbor, heat + graph.get(location).getNeighborHeat(neighbor));
            }
        }
    }


    @Override
    public Integer firstStar() {
        HeatMap heatMap = this.getInput(Day17::parseHeatMap);

        return new HeatGraph(heatMap, 1, 3).run();
    }

    @Override
    public Integer secondStar() {
        HeatMap heatMap = this.getInput(Day17::parseHeatMap);

        return new HeatGraph(heatMap, 4, 10).run();
    }


    private static HeatMap parseHeatMap(List<String> input) {
        return new HeatMap(input, input.get(0).length(), input.size());
    }
}
