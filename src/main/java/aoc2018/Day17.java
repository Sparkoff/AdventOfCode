package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day17 extends DayBase<Day17.Reservoir, Integer, Integer> {

    public Day17() {
        super();
    }

    public Day17(List<String> input) {
        super(input);
    }

    record Point(int x, int y) {
        public static final Point spring = new Point(500, 0);

        public Point up() {
            return new Point(x, y - 1);
        }
        public Point down() {
            return new Point(x, y + 1);
        }
        public Point left() {
            return new Point(x - 1, y);
        }
        public Point right() {
            return new Point(x + 1, y);
        }

        public static List<Point> fromSquareMeters(int xMin, int xMax, int yMin, int yMax) {
            return IntStream.rangeClosed(xMin, xMax)
                    .mapToObj(x -> IntStream.rangeClosed(yMin, yMax)
                            .mapToObj(y -> new Point(x, y))
                            .toList())
                    .flatMap(List::stream)
                    .toList();
        }
    }
    record ReservoirBox(int xMin, int xMax, int yMin, int yMax) {
        public boolean isOutside(Point p) {
            return p.x() < xMin || p.x() > xMax || p.y() > yMax;
        }
    }
    static class Reservoir {
        private final Set<Point> clay;
        private final Set<Point> restWater = new HashSet<>();
        private final Set<Point> passingWater = new HashSet<>();
        private final ReservoirBox box;

        public Reservoir(Set<Point> clay, ReservoirBox box) {
            this.clay = clay;
            this.box = box;
        }

        public void spill() {
            if (!restWater.isEmpty()) return;

            List<Point> toVisit = new ArrayList<>();
            toVisit.add(Point.spring.down());
            while (!toVisit.isEmpty()) {
                Point current = toVisit.remove(0);

                if (box.isOutside(current)) continue;

                if (isFree(current) && current.y() >= box.yMin()) {
                    passingWater.add(current);
                }

                Point nextDown = current.down();
                if (isFree(nextDown)) {
                    toVisit.add(nextDown);
                } else if (isHorizontalObstacle(nextDown)) {
                    Point nextLeft = current.left();
                    Point nextRight = current.right();

                    if (isFree(nextLeft)) {
                        toVisit.add(nextLeft);
                    }
                    if (isFree(nextRight)) {
                        toVisit.add(nextRight);
                    }
                    if (isVerticalObstacle(nextLeft) && isVerticalObstacle(nextRight)) {
                        Point explore = current;
                        while (isWater(explore)) {
                            explore = explore.left();
                        }
                        if (!clay.contains(explore)) {
                            continue;
                        }

                        explore = current;
                        while (isWater(explore)) {
                            explore = explore.right();
                        }
                        if (!clay.contains(explore)) {
                            continue;
                        }

                        passingWater.remove(current);
                        restWater.add(current);

                        Point currentBackward = current.up();
                        if (passingWater.contains(currentBackward)) {
                            toVisit.add(currentBackward);
                        }

                        explore = current.left();
                        while (isWater(explore)) {
                            passingWater.remove(explore);
                            restWater.add(explore);
                            Point exploreBackward = explore.up();
                            if (passingWater.contains(exploreBackward)) {
                                toVisit.add(exploreBackward);
                            }
                            explore = explore.left();
                        }

                        explore = current.right();
                        while (isWater(explore)) {
                            passingWater.remove(explore);
                            restWater.add(explore);
                            Point exploreBackward = explore.up();
                            if (passingWater.contains(exploreBackward)) {
                                toVisit.add(exploreBackward);
                            }
                            explore = explore.right();
                        }
                    }
                }
            }
        }

        private boolean isFree(Point p) {
            return !(clay.contains(p) || isWater(p));
        }
        private boolean isHorizontalObstacle(Point p) {
            return clay.contains(p) || restWater.contains(p);
        }
        private boolean isVerticalObstacle(Point p) {
            return clay.contains(p) || passingWater.contains(p);
        }
        private boolean isWater(Point p) {
            return restWater.contains(p) || passingWater.contains(p);
        }

        public int totalWater() {
            return passingWater.size() + restWater.size();
        }
        public int totalRestWater() {
            return restWater.size();
        }

        public void print() {
            List<String> scan = new ArrayList<>();
            for (int y = 0; y <= box.yMax(); y++) {
                StringBuilder row = new StringBuilder();
                for (int x = box.xMin(); x <= box.xMax(); x++) {
                    Point p = new Point(x, y);
                    if (restWater.contains(p)) {
                        row.append('~');
                    } else if (passingWater.contains(p)) {
                        row.append('|');
                    } else if (clay.contains(p)) {
                        row.append('#');
                    } else if (p.equals(Point.spring)) {
                        row.append('+');
                    } else {
                        row.append('.');
                    }
                }
                scan.add(row.toString());
            }
            System.out.println(String.join("\n", scan));
        }
    }


    @Override
    public Integer firstStar() {
        Reservoir reservoir = this.getInput(Day17::parseClaySquareMeters);
        reservoir.spill();
        //reservoir.print();
        return reservoir.totalWater();
    }

    @Override
    public Integer secondStar() {
        Reservoir reservoir = this.getInput(Day17::parseClaySquareMeters);
        reservoir.spill();

        return reservoir.totalRestWater();
    }


    private static Reservoir parseClaySquareMeters(List<String> input) {
        Pattern pattern = Pattern.compile("([xy])=(\\d+),\\s[xy]=(\\d+)\\.\\.(\\d+)");
        Set<Point> claySquareMeters = input.stream()
                .map(line -> {
                    Matcher m = pattern.matcher(line);
                    if (m.find()) {
                        if (m.group(1).equals("x")) {
                            return Point.fromSquareMeters(
                                    Integer.parseInt(m.group(2)),
                                    Integer.parseInt(m.group(2)),
                                    Integer.parseInt(m.group(3)),
                                    Integer.parseInt(m.group(4))
                            );
                        } else {
                            return Point.fromSquareMeters(
                                    Integer.parseInt(m.group(3)),
                                    Integer.parseInt(m.group(4)),
                                    Integer.parseInt(m.group(2)),
                                    Integer.parseInt(m.group(2))
                            );
                        }
                    }
                    throw new IllegalArgumentException("Can't parse following square meter : " + line);
                })
                .flatMap(List::stream)
                .collect(Collectors.toSet());

        ReservoirBox box = new ReservoirBox(
                claySquareMeters.stream()
                        .mapToInt(Point::x)
                        .min()
                        .orElseThrow() - 1,
                claySquareMeters.stream()
                        .mapToInt(Point::x)
                        .max()
                        .orElseThrow() + 1,
                claySquareMeters.stream()
                        .mapToInt(Point::y)
                        .min()
                        .orElseThrow(),
                claySquareMeters.stream()
                        .mapToInt(Point::y)
                        .max()
                        .orElseThrow()
        );

        return new Reservoir(claySquareMeters, box);
    }
}
