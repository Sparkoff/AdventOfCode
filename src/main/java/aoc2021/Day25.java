package aoc2021;

import common.DayBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Day25 extends DayBase<Day25.SeaFloor, Integer, Integer> {

    public Day25() {
        super();
    }

    public Day25(List<String> input) {
        super(input);
    }

    public record Coord(int x, int y) {}
    public record SeaFloor(int height, int width, Set<Coord> east, Set<Coord> south, boolean hasMoved) {
        public SeaFloor(int height, int width, Set<Coord> east, Set<Coord> south) {
            this(height, width, east, south, true);
        }

        public Coord goEast(Coord c) {
            return new Coord((c.x == width - 1) ? 0 : c.x + 1, c.y);
        }
        public Coord goSouth(Coord c) {
            return new Coord(c.x, (c.y == height - 1) ? 0 : c.y + 1);
        }
    }

    @Override
    public Integer firstStar() {
        SeaFloor seaFloor = this.getInput(Day25::parseSeaFloor);

        int stepCount = 0;
        while(seaFloor.hasMoved) {
            stepCount++;
            seaFloor = computeNextStep(seaFloor);
        }

        return stepCount;
    }

    @Override
    public Integer secondStar() {
        return 2021;
    }

    public static SeaFloor computeNextStep(SeaFloor seaFloor) {
        // Phase 1: East-moving cucumbers
        Set<Coord> allCucumbers = new HashSet<>(seaFloor.east());
        allCucumbers.addAll(seaFloor.south());

        Set<Coord> newEast = new HashSet<>();
        int movedCountEast = 0;
        for (Coord seaCucumber : seaFloor.east()) {
            Coord next = seaFloor.goEast(seaCucumber);
            if (allCucumbers.contains(next)) {
                newEast.add(seaCucumber);
            } else {
                newEast.add(next);
                movedCountEast++;
            }
        }

        // Phase 2: South-moving cucumbers
        allCucumbers = new HashSet<>(newEast);
        allCucumbers.addAll(seaFloor.south());

        Set<Coord> newSouth = new HashSet<>();
        int movedCountSouth = 0;
        for (Coord seaCucumber : seaFloor.south()) {
            Coord next = seaFloor.goSouth(seaCucumber);
            if (allCucumbers.contains(next)) {
                newSouth.add(seaCucumber);
            } else {
                newSouth.add(next);
                movedCountSouth++;
            }
        }

        return new SeaFloor(seaFloor.height(), seaFloor.width(), newEast, newSouth, (movedCountEast + movedCountSouth) > 0);
    }

    private static SeaFloor parseSeaFloor(List<String> input) {
        Set<Coord> east = new HashSet<>();
        Set<Coord> south = new HashSet<>();

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                char c = input.get(y).charAt(x);
                if (c == '>') {
                    east.add(new Coord(x, y));
                } else if (c == 'v') {
                    south.add(new Coord(x, y));
                }
            }
        }

        return new SeaFloor(input.size(), input.get(0).length(), east, south);
    }
}