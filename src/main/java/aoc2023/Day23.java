package aoc2023;

import common.DayBase;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class Day23 extends DayBase<Day23.TrailMap, Integer, Integer> {

    public Day23() {
        super();
    }

    public Day23(List<String> input) {
        super(input);
    }

    enum Dir { UP, DOWN, LEFT, RIGHT }
    record Pt(int x, int y) {
        public Pt next(Dir d) {
            return switch (d) {
                case UP -> new Pt(x, y - 1);
                case DOWN -> new Pt(x, y + 1);
                case LEFT -> new Pt(x - 1, y);
                case RIGHT -> new Pt(x + 1, y);
            };
        }
    }
    record Node(Pt p, int l) {}

    record TrailMap(List<String> map, int width, int height) {
        public TrailMap(List<String> map) {
            this(map, map.get(0).length(), map.size());
        }

        public char get(Pt p) {
            return map.get(p.y()).charAt(p.x());
        }

        public boolean isTrail(Pt p) {
            return p.x() >= 0 && p.x() < width &&
                    p.y() >= 0 && p.y() < height &&
                    get(p) != '#';
        }

        public boolean isSlope(Pt p) {
            return List.of('>', '<', '^', 'v').contains(get(p));
        }

        public Pt start() {
            return new Pt(1, 0);
        }
        public Pt end() {
            return new Pt(width - 2, height - 1);
        }
    }


    @Override
    public Integer firstStar() {
        TrailMap map = this.getInput(Day23::parseTrailMap);
        Map<Pt, List<Node>> graph = computeGraph(map, true);

        return longestHike(graph, map.start(), map.end());
    }

    @Override
    public Integer secondStar() {
        TrailMap map = this.getInput(Day23::parseTrailMap);
        Map<Pt, List<Node>> graph = computeGraph(map, false);

        return longestHike(graph, map.start(), map.end());
    }

    private List<Pt> getNeighbors(Pt p, TrailMap map, boolean slopes) {
        char current = slopes ? map.get(p) : '.';
        List<Dir> nextDirs = switch(current) {
            case '>' -> List.of(Dir.RIGHT);
            case '<' -> List.of(Dir.LEFT);
            case '^' -> List.of(Dir.UP);
            case 'v' -> List.of(Dir.DOWN);
            case '.' -> List.of(Dir.UP, Dir.DOWN, Dir.LEFT, Dir.RIGHT);
            default -> throw new IllegalStateException("Unexpected value: " + current);
        };

        return nextDirs.stream()
                .map(p::next)
                .filter(map::isTrail)
                .toList();
    }

    private Map<Pt, List<Node>> computeGraph(TrailMap map, boolean slope) {
        Queue<Pt> nexts = new ArrayDeque<>();
        Set<Pt> visited = new HashSet<>();
        Map<Pt, List<Node>> graph = new HashMap<>();

        nexts.add(map.start());
        while (!nexts.isEmpty()) {
            Pt current = nexts.poll();

            if (visited.contains(current)) {
                continue;
            }
            graph.put(current, new ArrayList<>());

            for (Pt next : getNeighbors(current, map, slope)) {
                int length = 1;
                Pt prev = current;
                Pt position = next;
                boolean deadEnd = false;

                while (true) {
                    List<Pt> neighbors = getNeighbors(position, map, slope);

                    if (neighbors.size() == 1 && neighbors.contains(prev) && map.isSlope(position)) {
                        deadEnd = true;
                        break;
                    }
                    if (neighbors.size() != 2) {
                        break;
                    }

                    for (Pt neighbor : neighbors) {
                        if (!neighbor.equals(prev)) {
                            length++;
                            prev = position;
                            position = neighbor;
                            break;
                        }
                    }
                }

                if (deadEnd) {
                    continue;
                }

                graph.get(current).add(new Node(position, length));
                nexts.add(position);
            }
            visited.add(current);
        }
        return graph;
    }

    private int longestHike(Map<Pt, List<Node>> graph, Pt start, Pt end) {
        return exploreGraph(graph, start, end, 0, new HashSet<>(Set.of(start)));
    }

    private int exploreGraph(Map<Pt, List<Node>> graph, Pt current, Pt end, int currentLength, Set<Pt> visited) {
        if (current.equals(end)) {
            return currentLength;
        }

        int maxLength = 0;
        for (Node neighbor : graph.get(current)) {
            if (visited.add(neighbor.p())) { // If the point was not already visited, add it and proceed
                int length = exploreGraph(graph, neighbor.p(), end, currentLength + neighbor.l(), visited);
                if (length > maxLength) {
                    maxLength = length;
                }
                visited.remove(neighbor.p()); // Backtrack: remove the point for other paths to use
            }
        }
        return maxLength;
    }

    private static TrailMap parseTrailMap(List<String> input) {
        return new TrailMap(input);
    }
}
