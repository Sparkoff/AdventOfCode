package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Day22 extends DayBase<Map<Integer, Day22.Node>, Integer, Integer> {

    public Day22() {
        super();
    }

    public Day22(List<String> input) {
        super(input);
    }

    record Pt(int x, int y, int z) {
        public Pt goDown() {
            return new Pt(x, y, z - 1);
        }
    }
    record Brick(Pt begin, Pt end) {
        public List<Pt> getCubes() {
            List<Pt> cubes = new ArrayList<>();
            for (int x = begin.x(); x <= end.x(); x++) {
                for (int y = begin.y(); y <= end.y(); y++) {
                    for (int z = begin.z(); z <= end.z(); z++) {
                        cubes.add(new Pt(x, y, z));
                    }
                }
            }
            return cubes;
        }

        public Brick goDown() {
            return new Brick(begin.goDown(), end.goDown());
        }

        public boolean layOn(Brick other) {
            return this.begin.z() == other.end.z() + 1 &&
                    (this.begin.x() <= other.end.x() && this.end.x() >= other.begin.x()) &&
                    (this.begin.y() <= other.end.y() && this.end.y() >= other.begin.y());
        }
    }
    record Node(int id, List<Integer> up, List<Integer> down) {}


    @Override
    public Integer firstStar() {
        Map<Integer, Node> brickLayer = this.getInput(Day22::parseSnapshot);

        List<Integer> singleSupport = brickLayer.values().stream()
                .map(Node::down)
                .filter(down -> down.size() == 1)
                .flatMap(List::stream)
                .distinct()
                .toList();
        List<Integer> duplicatedSupport = brickLayer.values().stream()
                .map(Node::down)
                .filter(down -> down.size() > 1)
                .flatMap(List::stream)
                .distinct()
                .filter(b -> !singleSupport.contains(b))
                .toList();

        List<Integer> notSupporting = brickLayer.values().stream()
                .filter(n -> n.up().isEmpty())
                .map(Node::id)
                .toList();

        return duplicatedSupport.size() + notSupporting.size();
    }

    @Override
    public Integer secondStar() {
        Map<Integer, Node> brickLayer = this.getInput(Day22::parseSnapshot);

        return brickLayer.values().stream()
                .map(Node::down)
                .filter(down -> down.size() == 1)
                .flatMap(List::stream)
                .distinct()
                .mapToInt(id -> {
                    Set<Integer> falls = new HashSet<>();
                    falls.add(id);

                    List<Integer> nexts = new ArrayList<>(brickLayer.get(id).up());
                    while (!nexts.isEmpty()) {
                        int current = nexts.remove(0);
                        if (falls.contains(current)) {
                            continue;
                        }

                        if (falls.containsAll(brickLayer.get(current).down())) {
                            falls.add(current);
                            nexts.addAll(brickLayer.get(current).up());
                        }
                    }
                    return falls.size() - 1;
                })
                .sum();
    }


    private static Map<Integer, Node> parseSnapshot(List<String> input) {
        List<Brick> snapshot = input.stream()
                .map(l -> l.split("~"))
                .map(l -> Arrays.stream(l)
                        .map(p -> Arrays.stream(p.split(","))
                                .map(Integer::parseInt)
                                .toList()
                        )
                        .map(p -> new Pt(p.get(0), p.get(1), p.get(2)))
                        .toList()
                )
                .map(l -> new Brick(l.get(0), l.get(1)))
                .sorted(Comparator.comparingInt(b -> b.begin().z()))
                .collect(Collectors.toList());

        moveToStack(snapshot);

        Map<Integer, List<Integer>> ups = new HashMap<>();
        Map<Integer, List<Integer>> downs = new HashMap<>();
        for (int i = 0; i < snapshot.size(); i++) {
            ups.put(i, new ArrayList<>());
            downs.put(i, new ArrayList<>());

            Brick current = snapshot.get(i);
            for (int j = 0; j < i; j++) {
                if (current.layOn(snapshot.get(j))) {
                    ups.get(j).add(i);
                    downs.get(i).add(j);
                }
            }
        }

        return ups.keySet().stream()
                .map(i -> new Node(i, ups.get(i), downs.get(i)))
                .collect(Collectors.toMap(Node::id, n -> n));
    }

    private static void moveToStack(List<Brick> bricks) {
        boolean stacked = false;
        while (!stacked) {
            stacked = true;

            for (int i = 0; i < bricks.size(); i++) {
                Brick current = bricks.get(i);

                if (current.begin().z() != 1) {
                    Brick finalCurrent = current;
                    List<Pt> blocks = bricks.stream()
                            .filter(b -> !b.equals(finalCurrent))
                            .map(Brick::getCubes)
                            .flatMap(List::stream)
                            .toList();

                    boolean moved = false;
                    Brick next = current.goDown();
                    while (next.begin().z() > 0 && Collections.disjoint(blocks, next.getCubes())) {
                        moved = true;
                        current = next;
                        next = current.goDown();
                    }

                    if (moved) {
                        stacked = false;
                        bricks.set(i, current);
                    }
                }
            }
        }
    }
}
