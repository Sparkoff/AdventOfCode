package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Comparator.comparingInt;


public class Day22 extends DayBase<Map<Integer, Day22.Node>, Integer, Integer> {

    public Day22() {
        super();
    }

    public Day22(List<String> input) {
        super(input);
    }

    record Pt(int x, int y, int z) {
        public Pt goDown(int fallDistance) {
            return new Pt(x, y, z - fallDistance);
        }
    }
    record Brick(Pt begin, Pt end) {}
    record Node(int id, List<Integer> up, List<Integer> down) {}


    @Override
    public Integer firstStar() {
        Map<Integer, Node> brickLayer = this.getInput(Day22::parseSnapshot);

        // A brick can be disintegrated if it's not the sole support for any other brick.
        return (int) brickLayer.values().stream()
                .filter(node -> {
                    // Find all bricks supported by this node.
                    return node.up().stream()
                            // Check if any of those supported bricks have ONLY this node as support.
                            .noneMatch(supportedId -> brickLayer.get(supportedId).down().size() == 1);
                })
                .count();
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
        List<Brick> bricks = new ArrayList<>(input.stream()
                .map(l -> l.split("~"))
                .map(l -> Arrays.stream(l)
                        .map(p -> Arrays.stream(p.split(","))
                                .map(Integer::parseInt)
                                .toList()
                        )
                        .map(p -> new Pt(p.get(0), p.get(1), p.get(2)))
                        .toList()
                )
                .map(l -> new Brick(l.get(0), l.get(1))).toList());

        bricks.sort(comparingInt(b -> b.begin().z()));

        // Height map for the XY plane
        Map<Pt, Integer> heightMap = new HashMap<>();
        Map<Pt, Integer> idMap = new HashMap<>();

        Map<Integer, Node> nodes = new HashMap<>();

        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            int maxHeight = 0;
            Set<Integer> supportingIds = new HashSet<>();

            // Find the ground level for the current brick
            for (int x = brick.begin.x(); x <= brick.end.x(); x++) {
                for (int y = brick.begin.y(); y <= brick.end.y(); y++) {
                    Pt groundPt = new Pt(x, y, 0);
                    int currentHeight = heightMap.getOrDefault(groundPt, 0);
                    if (currentHeight > maxHeight) {
                        maxHeight = currentHeight;
                        supportingIds.clear();
                        supportingIds.add(idMap.get(groundPt));
                    } else if (currentHeight == maxHeight && maxHeight > 0) {
                        supportingIds.add(idMap.get(groundPt));
                    }
                }
            }

            // Move brick down to its final resting Z position
            int fallDistance = brick.begin.z() - (maxHeight + 1);
            Brick settledBrick = brick;
            if (fallDistance > 0) {
                settledBrick = new Brick(brick.begin.goDown(fallDistance), brick.end.goDown(fallDistance));
            }

            // Update the height map with the new brick's info
            for (int x = settledBrick.begin.x(); x <= settledBrick.end.x(); x++) {
                for (int y = settledBrick.begin.y(); y <= settledBrick.end.y(); y++) {
                    Pt groundPt = new Pt(x, y, 0);
                    heightMap.put(groundPt, settledBrick.end.z());
                    idMap.put(groundPt, i);
                }
            }

            // Create the node and link dependencies
            nodes.put(i, new Node(i, new ArrayList<>(), new ArrayList<>(supportingIds)));
            for (int supporterId : supportingIds) {
                nodes.get(supporterId).up().add(i);
            }
        }

        return nodes;
    }
}
