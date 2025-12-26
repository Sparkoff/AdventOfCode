package aoc2025;

import common.DayBase;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;


public class Day9 extends DayBase<List<Day9.Pt>, Long, Long> {

    public Day9() {
        super();
    }

    public Day9(List<String> input) {
        super(input);
    }

    record Pt(int x, int y) implements Comparable<Pt> {
        public List<Pt> adjacents() {
            return List.of(
                new Pt(x - 1, y),
                new Pt(x + 1, y),
                new Pt(x, y - 1),
                new Pt(x, y + 1)
            );
        }
        public static Pt fromCoordinates(List<Integer> coordinates) {
            return new Pt(coordinates.get(0), coordinates.get(1));
        }

        @Override
        public int compareTo(Pt o) {
            if (this.x() != o.x()) {
                return Integer.compare(this.x(), o.x());
            } else {
                return Integer.compare(this.y(), o.y());
            }
        }
    }

    record Rectangle(Pt p1, Pt p2) implements Comparable<Rectangle> {
        public static long area(Pt p1, Pt p2) {
            int dx = Math.abs(p1.x - p2.x) + 1;
            int dy = Math.abs(p1.y - p2.y) + 1;
            return (long) dx * dy;
        }
        public static List<Pt> line(Pt p1, Pt p2) {
            return IntStream.rangeClosed(Math.min(p1.x(), p2.x()), Math.max(p1.x(), p2.x()))
                .boxed()
                .flatMap(x -> IntStream.rangeClosed(Math.min(p1.y(), p2.y()), Math.max(p1.y(), p2.y()))
                    .mapToObj(y -> new Pt(x, y)))
                .toList();
        }
        public static List<Pt> border(Pt p1, Pt p2) {
            Set<Pt> outerPoints = new HashSet<>();
            int x1 = p1.x();
            int y1 = p1.y();
            int x2 = p2.x();
            int y2 = p2.y();

            outerPoints.addAll(Rectangle.line(p1, new Pt(x1, y2)));
            outerPoints.addAll(Rectangle.line(new Pt(x1, y2), p2));
            outerPoints.addAll(Rectangle.line(p2, new Pt(x2, y1)));
            outerPoints.addAll(Rectangle.line(new Pt(x2, y1), p1));

            return outerPoints.stream().toList();
        }

        @Override
        public int compareTo(Rectangle o) {
            return Long.compare(area(p1, p2), area(o.p1, o.p2));
        }
    }

    // Coordinate Compression System
    //   ex: points [5, 2, 14, 22] compressed turned into [2, 1, 3, 4] using conversion table [2, 5, 14, 22] -> [1, 2, 3, 4]
    //   in such system, point 3 is between 2 and 5, so belong to group of 2, then 3 compressed turn into 1
    static class CCS {
        private final List<Integer> xCoords;
        private final List<Integer> yCoords;
        private final List<Pt> compressedPoints;

        public CCS(List<Pt> points) {
            this.xCoords = points.stream().map(Pt::x).distinct().sorted().toList();
            this.yCoords = points.stream().map(Pt::y).distinct().sorted().toList();
            this.compressedPoints = points.stream().map(this::convertPt).toList();
        }

        public Pt convertPt(Pt p) {
            return new Pt(findCompressed(xCoords, p.x()), findCompressed(yCoords, p.y()));
        }

        private static int findCompressed(List<Integer> coords, int real) {
            int index = Collections.binarySearch(coords, real);
            if (index >= 0) {
                return index;
            } else {
                // binarySearch returns (-(insertion point) - 1) if not found.
                // We want the index of the element just *before* the insertion point (floor).
                int insertionPoint = -(index + 1);
                return Math.max(0, insertionPoint - 1);
            }
        }

        public long computeRealArea(Pt p1, Pt p2) {
            return Rectangle.area(
                new Pt(xCoords.get(p1.x()), yCoords.get(p1.y())),
                new Pt(xCoords.get(p2.x()), yCoords.get(p2.y()))
            );
        }

        public int getCompressedWidth() {
            return xCoords.size();
        }

        public int getCompressedHeight() {
            return yCoords.size();
        }

        public List<Pt> getCompressedPoints() {
            return compressedPoints;
        }
    }


    @Override
    public Long firstStar() {
        List<Pt> redTiles = this.getInput(Day9::parseRedTiles);

        return IntStream.range(0, redTiles.size() - 1)
            .mapToLong(i -> IntStream.range(i + 1, redTiles.size())
                .mapToLong(j -> Rectangle.area(redTiles.get(i), redTiles.get(j)))
                .max()
                .orElse(0))
            .max()
            .orElse(0);
    }

    @Override
    public Long secondStar() {
        List<Pt> redTiles = this.getInput(Day9::parseRedTiles);

        CCS ccs = new CCS(redTiles);

        // Create a grid representing red/green tiles in compressed space
        boolean[][] compressedSpace = compressedSpacePresence(ccs);

        long maxArea = 0;
        for (int i = 0; i < ccs.getCompressedPoints().size(); i++) {
            Pt p1 = ccs.getCompressedPoints().get(i);
            for (int j = i + 1; j < ccs.getCompressedPoints().size(); j++) {
                Pt p2 = ccs.getCompressedPoints().get(j);

                if (p1.x() == p2.x() || p1.y() == p2.y()) {
                    continue;
                }

                int minX = Math.min(p1.x(), p2.x());
                int maxX = Math.max(p1.x(), p2.x());
                int minY = Math.min(p1.y(), p2.y());
                int maxY = Math.max(p1.y(), p2.y());

                // Check rectangle border is never outside the tiles
                // optimization: Check rows and column separately to fail early
                boolean borderValid = true;
                for (int x = minX; x <= maxX; x++) {
                    // Check top and bottom rows
                    if (!compressedSpace[x][minY] || !compressedSpace[x][maxY]) {
                        borderValid = false;
                        break;
                    }
                }
                if (borderValid) {
                    for (int y = minY; y <= maxY; y++) {
                        // Check left and right columns
                        if (!compressedSpace[minX][y] || !compressedSpace[maxX][y]) {
                            borderValid = false;
                            break;
                        }
                    }
                }
                if (borderValid) {
                     maxArea = Math.max(maxArea, ccs.computeRealArea(p1, p2));
                }
            }
        }

        return maxArea;
    }


    private static List<Pt> parseRedTiles(List<String> input) {
        return input.stream()
            .map(l -> Arrays.stream(l.split(",")).map(Integer::parseInt).toList())
            .map(Pt::fromCoordinates)
            .toList();
    }

    private static boolean[][] compressedSpacePresence(CCS ccs) {
        int width = ccs.getCompressedWidth();
        int height = ccs.getCompressedHeight();

        // add each pair of points as lines and do not forget to close the loop
        Set<Pt> tilesLoop = new HashSet<>();
        List<Pt> cps = ccs.getCompressedPoints();
        for (int i = 0; i < cps.size() - 1; i++) {
            tilesLoop.addAll(Rectangle.line(cps.get(i), cps.get(i + 1)));
        }
        tilesLoop.addAll(Rectangle.line(cps.get(0), cps.get(cps.size() - 1)));

        // init queue with map borders that are NOT part of the loop
        Deque<Pt> queue = new ArrayDeque<>();
        List<Pt> borders = Rectangle.border(new Pt(0, 0), new Pt(width - 1, height - 1));
        for (Pt p : borders) {
             if (!tilesLoop.contains(p)) {
                 queue.add(p);
             }
        }

        Set<Pt> outside = new HashSet<>(queue);
        
        while (!queue.isEmpty()) {
            Pt p = queue.poll();
            
            for (Pt adj : p.adjacents()) {
                if (adj.x() >= 0 && adj.x() < width && adj.y() >= 0 && adj.y() < height) {
                    if (!tilesLoop.contains(adj) && !outside.contains(adj)) {
                        outside.add(adj);
                        queue.add(adj);
                    }
                }
            }
        }

        boolean[][] grid = new boolean[width][height];
        for (boolean[] row : grid) {
            Arrays.fill(row, true);
        }
        for (Pt p : outside) {
            grid[p.x()][p.y()] = false;
        }

        return grid;
    }
}
