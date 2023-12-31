package aoc2018;

import common.DayBase;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class Day23 extends DayBase<List<Day23.Nanobot>, Integer, Integer> {

    public Day23() {
        super();
    }

    public Day23(List<String> input) {
        super(input);
    }

    record Point(int x, int y, int z) {
        public static final Point origin = new Point(0, 0, 0);

        public int manhattan(Point other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
        }

        public Point translate(int scale) {
            return new Point(x + scale, y + scale, z + scale);
        }

        public static Point min(List<Point> points) {
            return new Point(
                    points.stream()
                            .mapToInt(Point::x)
                            .min()
                            .orElseThrow(),
                    points.stream()
                            .mapToInt(Point::y)
                            .min()
                            .orElseThrow(),
                    points.stream()
                            .mapToInt(Point::z)
                            .min()
                            .orElseThrow()
            );
        }
        public static Point max(List<Point> points) {
            return new Point(
                    points.stream()
                            .mapToInt(Point::x)
                            .max()
                            .orElseThrow(),
                    points.stream()
                            .mapToInt(Point::y)
                            .max()
                            .orElseThrow(),
                    points.stream()
                            .mapToInt(Point::z)
                            .max()
                            .orElseThrow()
            );
        }
    }
    record Nanobot(Point p, int range) {}

    record Candidate(Point center, int inRange) {
        public Candidate() {
            this(Point.origin, 0);
        }

        public Candidate compare(Candidate other) {
            if (inRange != other.inRange) {
                return inRange > other.inRange ? this : other;
            } else {
                return center.manhattan(Point.origin) < other.center.manhattan(Point.origin) ?
                        this :
                        other;
            }
        }
    }


    @Override
    public Integer firstStar() {
        List<Nanobot> nanobots = this.getInput(Day23::parseNanobots);

        Nanobot strongest = nanobots.stream()
                .max(Comparator.comparingInt(Nanobot::range))
                .orElseThrow();

        return Math.toIntExact(nanobots.stream()
                .map(Nanobot::p)
                .filter(p -> strongest.p().manhattan(p) <= strongest.range())
                .count());
    }

    @Override
    public Integer secondStar() {
        List<Nanobot> nanobots = this.getInput(Day23::parseNanobots);

        // Mean shift algorithm: search from macro view to micro.
        //   starts with a cube about cloud size, and search by a tenth of the sides length
        //   then reduce cube size around the best point

        List<Point> points = nanobots.stream()
                .map(Nanobot::p)
                .toList();
        Point min = Point.min(points);
        Point max = Point.max(points);

        Candidate best = new Candidate();
        int scale = computeScale(min, max);
        while (scale >= 1) {
            Point start = best.center().translate(scale * -10);
            Point end = best.center().translate(scale * 10);
            if (best.inRange() == 0) {
                start = min;
                end = max;
            }

            // reset best center
            best = new Candidate();
            for (int x = start.x; x <= end.x; x += scale) {
                for (int y = start.y; y <= end.y; y += scale) {
                    for (int z = start.z; z <= end.z; z += scale) {
                        Point p = new Point(x, y, z);
                        Candidate c = new Candidate(
                                p,
                                (int) nanobots.stream()
                                        .filter(n -> p.manhattan(n.p()) <= n.range())
                                        .count()
                        );

                        if (best.inRange() == 0) {
                            best = c;
                        } else {
                            best = best.compare(c);
                        }
                    }
                }
            }

            // reduce scale for next round
            scale /= 10;
        }


        return best.center().manhattan(Point.origin);
    }

    private static int computeScale(Point min, Point max) {
        int minDist = Stream.of(min.x(), min.y(), min.y(), max.x(), max.y(), max.y())
                .mapToInt(Math::abs)
                .min()
                .orElseThrow();
        int scale = 10;
        while (minDist > scale) {
            scale *= 10;
        }
        return scale / 10;
    }


    private static List<Nanobot> parseNanobots(List<String> input) {
        Pattern pattern = Pattern.compile("^pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)$");

        return input.stream()
                .map(l -> {
                    Matcher matcher = pattern.matcher(l);

                    if (!matcher.find()) {
                        throw new IllegalStateException("Unable to parse line: " + l);
                    }
                    return new Nanobot(
                            new Point(
                                    Integer.parseInt(matcher.group(1)),
                                    Integer.parseInt(matcher.group(2)),
                                    Integer.parseInt(matcher.group(3))
                            ),
                            Integer.parseInt(matcher.group(4))
                    );
                })
                .toList();
    }
}
