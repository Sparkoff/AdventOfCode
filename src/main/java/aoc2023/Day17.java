package aoc2023;

import common.DayBase;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


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

    enum Direction {
        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public Direction opposite() {
            return switch (this) {
                case UP -> DOWN;
                case DOWN -> UP;
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
            };
        }
    }

    // Represents a state in our search space
    record State(int heat, int x, int y, Direction dir, int steps) {}

    // A unique key for our 'visited' set
    record VisitedKey(int x, int y, Direction dir, int steps) {}

    private int findPath(HeatMap heatMap, int minSteps, int maxSteps) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::heat));
        Set<VisitedKey> visited = new HashSet<>();

        // Start by being able to move RIGHT or DOWN from (0,0)
        queue.add(new State(0, 0, 0, Direction.RIGHT, 0));
        queue.add(new State(0, 0, 0, Direction.DOWN, 0));

        while (!queue.isEmpty()) {
            State current = queue.poll();

            // If we reached the target, return the heat.
            // Because this is Dijkstra's, the first time we reach it is the shortest path.
            if (current.x == heatMap.width - 1 && current.y == heatMap.height - 1 && current.steps >= minSteps) {
                return current.heat;
            }

            VisitedKey key = new VisitedKey(current.x, current.y, current.dir, current.steps);
            if (visited.contains(key)) {
                continue;
            }
            visited.add(key);

            for (Direction nextDir : Direction.values()) {
                // Cannot reverse direction
                if (nextDir == current.dir.opposite()) {
                    continue;
                }

                // Handle step constraints
                if (nextDir == current.dir) { // Continue straight
                    if (current.steps < maxSteps) {
                        int nextX = current.x + nextDir.dx;
                        int nextY = current.y + nextDir.dy;
                        if (heatMap.exist(nextX, nextY)) {
                            int newHeat = current.heat + heatMap.getHeat(nextX, nextY);
                            queue.add(new State(newHeat, nextX, nextY, nextDir, current.steps + 1));
                        }
                    }
                } else { // Turn
                    if (current.steps >= minSteps) {
                        int nextX = current.x + nextDir.dx;
                        int nextY = current.y + nextDir.dy;
                        if (heatMap.exist(nextX, nextY)) {
                            int newHeat = current.heat + heatMap.getHeat(nextX, nextY);
                            queue.add(new State(newHeat, nextX, nextY, nextDir, 1));
                        }
                    }
                }
            }
        }
        return -1; // Should not be reached
    }

    @Override
    public Integer firstStar() {
        HeatMap heatMap = this.getInput(Day17::parseHeatMap);
        return findPath(heatMap, 1, 3);
    }

    @Override
    public Integer secondStar() {
        HeatMap heatMap = this.getInput(Day17::parseHeatMap);
        return findPath(heatMap, 4, 10);
    }


    private static HeatMap parseHeatMap(List<String> input) {
        return new HeatMap(input, input.get(0).length(), input.size());
    }
}
