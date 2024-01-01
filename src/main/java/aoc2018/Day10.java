package aoc2018;

import common.DayBase;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day10 extends DayBase<List<Day10.Star>, String, Integer> {

    private static final String HI = """
            #...#..###
            #...#...#.
            #...#...#.
            #####...#.
            #...#...#.
            #...#...#.
            #...#...#.
            #...#..###""";
    private static final String NBRALZPH = """
            #....#..#####...#####.....##....#.......######..#####...#....#
            ##...#..#....#..#....#...#..#...#............#..#....#..#....#
            ##...#..#....#..#....#..#....#..#............#..#....#..#....#
            #.#..#..#....#..#....#..#....#..#...........#...#....#..#....#
            #.#..#..#####...#####...#....#..#..........#....#####...######
            #..#.#..#....#..#..#....######..#.........#.....#.......#....#
            #..#.#..#....#..#...#...#....#..#........#......#.......#....#
            #...##..#....#..#...#...#....#..#.......#.......#.......#....#
            #...##..#....#..#....#..#....#..#.......#.......#.......#....#
            #....#..#####...#....#..#....#..######..######..#.......#....#""";

    public Day10() {
        super();
    }

    public Day10(List<String> input) {
        super(input);
    }

    record Point(int x, int y) {
        public Point sum(Point other, int factor) {
            return new Point(x + factor * other.x, y + factor * other.y);
        }
    }
    record Star(Point position, Point velocity) {
        public Point positionAt(int t) {
            return position.sum(velocity, t);
        }
    }
    record Window(Point bottomLeft, Point topRight) {
        public static Window fromStars(List<Point> stars) {
            List<Integer> xs = stars.stream()
                    .map(Point::x)
                    .distinct()
                    .toList();
            List<Integer> ys = stars.stream()
                    .map(Point::y)
                    .distinct()
                    .toList();
            return new Window(
                    new Point(Collections.min(xs), Collections.min(ys)),
                    new Point(Collections.max(xs), Collections.max(ys))
            );
        }

        public long area() {
            return (long) (topRight().x() - bottomLeft().x()) * (topRight().y() - bottomLeft().y());
        }
    }
    record SkyState(List<Point> stars, Window w) {
        public SkyState(List<Point> stars) {
            this(stars, Window.fromStars(stars));
        }

        public long area() {
            return w.area();
        }

        public String read() {
            List<List<String>> sky = IntStream.rangeClosed(w.bottomLeft.y(), w.topRight.y())
                    .mapToObj(i -> IntStream.rangeClosed(w.bottomLeft.x(), w.topRight.x())
                            .mapToObj(j -> ".")
                            .collect(Collectors.toList()))
                    .toList();

            // new origin = bottom left of the sky window
            List<Point> movedStars = stars.stream()
                    .map(p -> p.sum(w.bottomLeft, -1))
                    .toList();
            for (Point p : movedStars) {
                sky.get(p.y()).set(p.x(), "#");
            }

            String skyDraw = String.join(
                    "\n",
                    sky.stream()
                            .map(l -> String.join("", l))
                            .toList()
            );
            System.out.println(skyDraw);

            return switch (skyDraw) {
                case HI -> "HI";
                case NBRALZPH -> "NBRALZPH";
                default -> "error";
            };
        }
    }
    record Sky(SkyState skyState, int t) {
        Sky(List<Point> sky, int t) {
            this(new SkyState(sky), t);
        }
    }


    @Override
    public String firstStar() {
        List<Star> stars = this.getInput(Day10::parseStars);

        return runStars(stars).skyState().read();
    }

    @Override
    public Integer secondStar() {
        List<Star> stars = this.getInput(Day10::parseStars);

        return runStars(stars).t();
    }

    private static Sky runStars(List<Star> stars) {
        Sky currentSky = new Sky(
                new SkyState(stars.stream().map(Star::position).toList()),
                0
        );

        boolean hasAligned = false;
        while (!hasAligned) {
            int t = currentSky.t() + 1;
            Sky sky = new Sky(stars.stream().map(s -> s.positionAt(t)).toList(), t);

            if (sky.skyState().area() > currentSky.skyState().area()) {
                hasAligned = true;
            } else {
                currentSky = sky;
            }
        }

        return currentSky;
    }

    private static List<Star> parseStars(List<String> input) {
        Pattern instructionPattern = Pattern.compile("position=<\\s*(-?\\d+),\\s*(-?\\d+)>\\svelocity=<\\s*(-?\\d+),\\s*(-?\\d+)>");

        return input.stream()
                .map(l -> {
                    Matcher m = instructionPattern.matcher(l);
                    if (!m.find()) throw new RuntimeException("Regex not matching for : " + l);
                    return m;
                })
                .map(m -> List.of(m.group(1), m.group(2), m.group(3), m.group(4)))
                .map(m -> m.stream()
                        .map(Integer::parseInt)
                        .toList()
                )
                .map(m -> new Star(
                        new Point(m.get(0), m.get(1)),
                        new Point(m.get(2), m.get(3))
                ))
                .toList();
    }
}
