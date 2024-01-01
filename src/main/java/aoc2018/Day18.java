package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Day18 extends DayBase<Day18.LumberArea, Integer, Integer> {

    public Day18() {
        super();
    }

    public Day18(List<String> input) {
        super(input);
    }

    record Point(int x, int y) implements Comparable<Point> {
        public List<Point> adjacents() {
            return List.of(
                    new Point(x - 1, y - 1),
                    new Point(x, y - 1),
                    new Point(x + 1, y - 1),
                    new Point(x - 1, y),
                    new Point(x + 1, y),
                    new Point(x - 1, y + 1),
                    new Point(x, y + 1),
                    new Point(x + 1, y + 1)
            );
        }

        @Override
        public int compareTo(Point other) {
            return Comparator.comparingInt(Point::y)
                    .thenComparingInt(Point::x)
                    .compare(this, other);
        }
    }
    enum LumberType {
        OPEN("o"),
        TREE("t"),
        LUMBER_YARD("l");

        private final String hash;

        LumberType(String hash) {
            this.hash = hash;
        }

        public String toHash() {
            return hash;
        }
    }
    record LumberArea(int width, int height, Map<Point,LumberType> lumbers) {
        public String getHash() {
            return lumbers.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .map(LumberType::toHash)
                    .collect(Collectors.joining(""));
        }

        public int getTotalResources() {
            return getTotalByType(LumberType.TREE) * getTotalByType(LumberType.LUMBER_YARD);
        }
        private int getTotalByType(LumberType type) {
            return (int) lumbers.values().stream()
                    .filter(lt -> lt == type)
                    .count();
        }
    }


    @Override
    public Integer firstStar() {
        LumberArea lumberArea = this.getInput(Day18::parseLumberCollectionArea);

        for (int i = 0; i < 10; i++) {
            lumberArea = nextGeneration(lumberArea);
        }

        return lumberArea.getTotalResources();
    }

    @Override
    public Integer secondStar() {
        LumberArea lumberArea = this.getInput(Day18::parseLumberCollectionArea);

        List<String> knownHashes = new ArrayList<>();
        Map<String,Integer> areaScores = new HashMap<>();

        knownHashes.add(lumberArea.getHash());
        areaScores.put(lumberArea.getHash(), lumberArea.getTotalResources());

        String hash;
        while (true) {
            lumberArea = nextGeneration(lumberArea);
            hash = lumberArea.getHash();

            if (!knownHashes.contains(hash)) {
                knownHashes.add(hash);
                areaScores.put(hash, lumberArea.getTotalResources());
            } else {
                break;
            }
        }

		long end = (long) 1E9;
        int patternStartId = knownHashes.indexOf(hash);
        int patternSize = knownHashes.size() - patternStartId;
        String finalHash = knownHashes.get(
                (int) (patternStartId + (end - knownHashes.size()) % patternSize)
        );

        return areaScores.get(finalHash);
    }

    private static LumberArea nextGeneration(LumberArea lumberArea) {
        Map<Point,LumberType> nextLumbers = new HashMap<>();

        for (int y = 0; y < lumberArea.height(); y++) {
            for (int x = 0; x < lumberArea.width(); x++) {
                Point p = new Point(x, y);
                List<LumberType> adjacents = p.adjacents().stream()
                        .map(ap -> lumberArea.lumbers().getOrDefault(ap, LumberType.OPEN))
                        .toList();
                LumberType nextType;
                if (lumberArea.lumbers().get(p) == LumberType.OPEN) {
                    nextType = checkType(adjacents, LumberType.TREE, 3) ? LumberType.TREE : LumberType.OPEN;
                } else if (lumberArea.lumbers().get(p) == LumberType.TREE) {
                    nextType = checkType(adjacents, LumberType.LUMBER_YARD, 3) ? LumberType.LUMBER_YARD : LumberType.TREE;
                } else {
                    nextType = checkType(adjacents, LumberType.LUMBER_YARD, 1) && checkType(adjacents, LumberType.TREE, 1) ?
                            LumberType.LUMBER_YARD :
                            LumberType.OPEN;
                }
                nextLumbers.put(p, nextType);
            }
        }

        return new LumberArea(lumberArea.width(), lumberArea.height(), nextLumbers);
    }

    private static boolean checkType(List<LumberType> adjacents, LumberType searchType, int searchCount) {
        return adjacents.stream()
                .filter(lt -> lt == searchType)
                .count() >= searchCount;
    }


    private static LumberArea parseLumberCollectionArea(List<String> input) {
        int width = input.get(0).length();
        int height = input.size();
        Map<Point,LumberType> lumbers = new HashMap<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                LumberType type = switch (input.get(y).charAt(x)) {
                    case '.' -> LumberType.OPEN;
                    case '|' -> LumberType.TREE;
                    case '#' -> LumberType.LUMBER_YARD;
                    default -> throw new IllegalStateException("Unknown lumber area type : " + input.get(y).charAt(x));
                };
                lumbers.put(new Point(x, y), type);
            }
        }

        return new LumberArea(width, height, lumbers);
    }
}
