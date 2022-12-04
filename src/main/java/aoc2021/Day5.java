package aoc2021;

import common.DayBase;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day5 extends DayBase<List<Day5.Vent>, Integer> {

    public Day5() {
        super();
    }

    public Day5(List<String> input) {
        super(input);
    }

    record Point(int x, int y) {}

    record Vent(Point p1, Point p2) {}

    @Override
    public Integer firstStar() {
        List<Vent> vents = this.getInput(Day5::parseVents).stream()
                .filter(v -> v.p1.x == v.p2.x || v.p1.y == v.p2.y)
                .toList();

        return countLinesOverlap(vents);
    }

    @Override
    public Integer secondStar() {
        List<Vent> vents = this.getInput(Day5::parseVents);

        return countLinesOverlap(vents);
    }

    private List<Point> getLinesFromVent(Vent vent) {
        if (vent.p1.x == vent.p2.x) {
            IntStream yRange;
            if (vent.p1.y < vent.p2.y) {
                yRange = IntStream.rangeClosed(vent.p1.y, vent.p2.y);
            } else {
                yRange = IntStream.rangeClosed(vent.p2.y, vent.p1.y);
            }
            return yRange.mapToObj(y -> new Point(vent.p1.x, y))
                    .toList();
        } else if (vent.p1.y == vent.p2.y) {
            IntStream xRange;
            if (vent.p1.x < vent.p2.x) {
                xRange = IntStream.rangeClosed(vent.p1.x, vent.p2.x);
            } else {
                xRange = IntStream.rangeClosed(vent.p2.x, vent.p1.x);
            }
            return xRange.mapToObj(x -> new Point(x, vent.p1.y))
                    .toList();
        } else {
            List<Integer> xRange;
            if (vent.p1.x < vent.p2.x) {
                xRange = IntStream.rangeClosed(vent.p1.x, vent.p2.x)
                        .boxed()
                        .toList();
            } else {
                xRange = IntStream.rangeClosed(vent.p2.x, vent.p1.x)
                        .boxed()
                        .sorted(Comparator.reverseOrder())
                        .toList();
            }
            List<Integer> yRange;
            if (vent.p1.y < vent.p2.y) {
                yRange = IntStream.rangeClosed(vent.p1.y, vent.p2.y)
                        .boxed()
                        .toList();
            } else {
                yRange = IntStream.rangeClosed(vent.p2.y, vent.p1.y)
                        .boxed()
                        .sorted(Comparator.reverseOrder())
                        .toList();
            }
            return IntStream.range(0, xRange.size())
                    .mapToObj(i -> new Point(xRange.get(i), yRange.get(i)))
                    .toList();
        }
    }

    private int countLinesOverlap(List<Vent> vents) {
        return (int) vents.stream()
                .map(this::getLinesFromVent)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(p -> p.getValue() > 1)
                .count();
    }

    private static List<Vent> parseVents(List<String> inputs) {
        return inputs.stream()
                .map(line -> Arrays.stream(line.split(" -> "))
                        .map(ends -> Arrays.stream(ends.split(","))
                                .map(Integer::parseInt)
                                .toList())
                        .map(ends -> new Point(ends.get(0), ends.get(1)))
                        .toList())
                .map(points -> new Vent(points.get(0), points.get(1)))
                .toList();
    }
}
