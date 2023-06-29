package aoc2017;

import common.DayBase;

import java.util.Arrays;
import java.util.List;


public class Day11 extends DayBase<List<String>, Integer> {

    public Day11() {
        super();
    }

    public Day11(List<String> input) {
        super(input);
    }

    record Step(int x, int y, int farPoint) {
        Step() {
            this(0, 0, 0);
        }
    }

    @Override
    public Integer firstStar() {
        List<String> path = this.getInput(Day11::parsePath);

        Step endPoint = run(path);
        return ManhattanDistance(endPoint.x(), endPoint.y());
    }

    @Override
    public Integer secondStar() {
        List<String> path = this.getInput(Day11::parsePath);

        return run(path).farPoint();

    }

    private Step run(List<String> path) {
        Step step = new Step();
        for (String dir : path) {
            int x = step.x();
            int y = step.y();
            switch (dir) {
                case "n" -> y++;
                case "nw" -> {
                    x--;
                    y++;
                }
                case "ne" -> x++;
                case "s" -> y--;
                case "sw" -> x--;
                case "se" -> {
                    x++;
                    y--;
                }
            }
            int distance = ManhattanDistance(x, y);
            step = new Step(x, y, Math.max(step.farPoint, distance));
        }

        return step;
    }

    private static int ManhattanDistance(int x, int y) {
        if (x == 0 || y == 0 || x * y > 0) {
            return Math.abs(x + y);
        }
        return Math.max(Math.abs(x), Math.abs(y));
    }

    private static List<String> parsePath(List<String> input) {
        return Arrays.stream(input.get(0).split(",")).toList();
    }
}
