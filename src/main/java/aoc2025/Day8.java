package aoc2025;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;


public class Day8 extends DayBase<List<Day8.Pt>, Integer, Long> {
    private int earlyPairStop = 1000;

    public Day8() {
        super();
    }

    public Day8(List<String> input, int earlyPairStop) {
        super(input);
        this.earlyPairStop = earlyPairStop;
    }
    public Day8(List<String> input) {
        super(input);
    }


    record Pt(int x, int y, int z) {
        public static double distance(Pt p1, Pt p2) {
            int dx = p1.x - p2.x;
            int dy = p1.y - p2.y;
            int dz = p1.z - p2.z;
            return Math.sqrt(((long) dx * dx + (long) dy * dy + (long) dz * dz));
        }

        public static Pt from3D(List<Integer> coordinates) {
            return new Pt(coordinates.get(0), coordinates.get(1), coordinates.get(2));
        }
    }
    record Circuit(Pt junction1, Pt junction2) implements Comparable<Circuit> {
        public double length() {
            return Pt.distance(junction1, junction2);
        }

        @Override
        public int compareTo(Circuit other) {
            return Double.compare(this.length(), other.length());
        }
    }

    /**
     * Disjoint Set Union (DSU) or Union-Find data structure.
     */
    static class DSU {
        private final Map<Pt, Pt> parent = new HashMap<>();
        private final Map<Pt, Integer> size = new HashMap<>();
        private int count;

        public DSU(List<Pt> points) {
            for (Pt p : points) {
                parent.put(p, p);
                size.put(p, 1);
            }
            this.count = points.size();
        }

        // Find the representative (root) of the set containing 'p'.
        public Pt findRoot(Pt p) {
            if (p.equals(parent.get(p))) {
                // p is the root of a set (even a single-element set, meaning not yet connected).
                return p;
            }

            // Path compression for optimization.
            Pt root = findRoot(parent.get(p));
            parent.put(p, root);
            return root;
        }

        // Union of the sets containing 'p1' and 'p2'.
        public void union(Pt p1, Pt p2) {
            Pt root1 = findRoot(p1);
            Pt root2 = findRoot(p2);
            if (!root1.equals(root2)) {
                // Union by size for optimization in root element search.
                if (size.get(root1) < size.get(root2)) {
                    Pt temp = root1;
                    root1 = root2;
                    root2 = temp;
                }
                parent.put(root2, root1);
                size.put(root1, size.get(root1) + size.get(root2));
                count--;
            }
        }

        public int getSetCount() {
            return count;
        }

        public List<Integer> getSetSizes() {
            return parent.entrySet().stream()
                    .filter(entry -> entry.getKey().equals(entry.getValue())) // Only roots
                    .map(entry -> size.get(entry.getKey()))
                    .toList();
        }
    }


    @Override
    public Integer firstStar() {
        List<Pt> junctionBoxes = this.getInput(Day8::parseJunctionBoxes);

        PriorityQueue<Circuit> queue = buildAllConnections(junctionBoxes);

        // Optimization for first star: only consider a subset of the shortest circuits.
        List<Circuit> tmp = new ArrayList<>();
        IntStream.range(0, earlyPairStop).forEach(i -> tmp.add(queue.poll()));
        queue.clear();
        queue.addAll(tmp);

        DSU dsu = new DSU(junctionBoxes);
        while (!queue.isEmpty()) {
            Circuit circuit = queue.poll();
            if (!dsu.findRoot(circuit.junction1()).equals(dsu.findRoot(circuit.junction2()))) {
                dsu.union(circuit.junction1(), circuit.junction2());
            }
        }

        return dsu.getSetSizes().stream()
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .reduce(1, (a, b) -> a * b);
    }

    @Override
    public Long secondStar() {
        List<Pt> junctionBoxes = this.getInput(Day8::parseJunctionBoxes);

        PriorityQueue<Circuit> queue = buildAllConnections(junctionBoxes);

        DSU dsu = new DSU(junctionBoxes);
        while (!queue.isEmpty()) {
            Circuit circuit = queue.poll();
            if (!dsu.findRoot(circuit.junction1()).equals(dsu.findRoot(circuit.junction2()))) {
                // If this is the last connection that will merge all points into a single component
                if (dsu.getSetCount() == 2) {
                     return (long) circuit.junction1().x() * circuit.junction2().x();
                }
                dsu.union(circuit.junction1(), circuit.junction2());
            }
        }

        throw new RuntimeException("Can't find last connection junction");
    }

    private PriorityQueue<Circuit> buildAllConnections(List<Pt> junctionBoxes) {
        PriorityQueue<Circuit> queue = new PriorityQueue<>();
        for (int i = 0; i < junctionBoxes.size() - 1; i++) {
            for (int j = i + 1; j < junctionBoxes.size(); j++) {
                queue.add(new Circuit(junctionBoxes.get(i), junctionBoxes.get(j)));
            }
        }
        return queue;
    }


    private static List<Pt> parseJunctionBoxes(List<String> input) {
        return input.stream()
            .map(l -> Arrays.stream(l.split(",")).map(Integer::parseInt).toList())
            .map(Pt::from3D)
            .toList();
    }
}
