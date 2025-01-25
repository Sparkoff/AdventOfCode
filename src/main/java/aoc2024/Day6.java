package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class Day6 extends DayBase<Day6.Lab, Integer, Integer> {

    public Day6() {
        super();
    }

    public Day6(List<String> input) {
        super(input);
    }

    enum Dir {
        UP, RIGHT, DOWN, LEFT;

        public Dir turn() {
            return switch (this) {
                case UP -> RIGHT;
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
            };
        }
    }
    record Pt(int x, int y) {
        public Pt next(Dir dir) {
            return switch (dir) {
                case UP -> new Pt(x, y - 1);
                case RIGHT -> new Pt(x + 1, y);
                case DOWN -> new Pt(x, y + 1);
                case LEFT -> new Pt(x - 1, y);
            };
        }
    }
    record Guard(Pt p, Dir dir) {
        public Pt next() {
            return p.next(dir);
        }
        public Guard turn() {
            return new Guard(p, dir.turn());
        }
        public Guard move() {
            return new Guard(p.next(dir), dir);
        }
    }
    record Lab(int w, int h, List<Pt> obstructions, Pt start) {
        public boolean isInLab(Guard g) {
            return g.p().x() >= 0 && g.p().x() < w && g.p().y() >= 0 && g.p().y() < h;
        }

        public Guard init() {
            return new Guard(start, Dir.UP);
        }

        public Lab addObstruction(Pt obstacle) {
            return new Lab(w, h, Stream.concat(obstructions.stream(), Stream.of(obstacle)).toList(), start);
        }
    }


    @Override
    public Integer firstStar() {
        Lab lab = this.getInput(Day6::parseLab);

        Set<Pt> visited = new HashSet<>();
        Guard guard = lab.init();

        while (lab.isInLab(guard)) {
            if (lab.obstructions().contains(guard.next())) {
                // hit obstacle, turn right
                guard = guard.turn();
            } else {
                // go straight
                visited.add(guard.p());
                guard = guard.move();
            }
        }

        return visited.size();
    }

    @Override
    public Integer secondStar() {
        Lab lab = this.getInput(Day6::parseLab);

        Set<Pt> visited = new HashSet<>();
        Guard guard = lab.init();

        while (lab.isInLab(guard)) {
            if (lab.obstructions().contains(guard.next())) {
                // hit obstacle, turn right
                guard = guard.turn();
            } else {
                // go straight
                visited.add(guard.p());
                guard = guard.move();
            }
        }

        // test by adding an obstacle on each visited location (except start position)
        int location = 0;
        for (Pt pt : visited) {
            if (pt.equals(lab.start())) {
                continue;
            }
            Lab labTest = lab.addObstruction(pt);

            Set<Guard> visitedTest = new HashSet<>();
            Guard guardTest = labTest.init();

            while (labTest.isInLab(guardTest)) {
                if (labTest.obstructions().contains(guardTest.next())) {
                    // hit obstacle, turn right
                    guardTest = guardTest.turn();
                } else if (visitedTest.add(guardTest)) {
                    // go straight
                    guardTest = guardTest.move();
                } else {
                    // path already exist, loop in progress
                    location++;
                    break;
                }
            }
        }

        return location;
    }


    private static Lab parseLab(List<String> input) {
        List<Pt> obstructions = new ArrayList<>();
        Pt start = new Pt(0, 0);

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);

                if (c == '#') {
                    obstructions.add(new Pt(x, y));
                } else if (c == '^') {
                    start = new Pt(x, y);
                }
            }
        }

        return new Lab(input.get(0).length(), input.size(), obstructions, start);
    }
}
