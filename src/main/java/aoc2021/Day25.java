package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;


public class Day25 extends DayBase<Day25.SeaFloor, Integer> {

    public Day25() {
        super();
    }

    public Day25(List<String> input) {
        super(input);
    }

    record Coord(int x, int y) {}
    record SeaFloor(int height, int width, List<Coord> east, List<Coord> south, boolean hasMoved) {
        public SeaFloor(int height, int width, List<Coord> east, List<Coord> south) {
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
        int movedCount = 0;

        List<Coord> newEast = new ArrayList<>();
        for (Coord seaCucumber : seaFloor.east()) {
            Coord next = seaFloor.goEast(seaCucumber);
            if (seaFloor.east().contains(next) || seaFloor.south().contains(next)) {
                newEast.add(seaCucumber);
            } else {
                newEast.add(next);
                movedCount++;
            }
        }

        List<Coord> newSouth = new ArrayList<>();
        for (Coord seaCucumber : seaFloor.south()) {
            Coord next = seaFloor.goSouth(seaCucumber);
            if (newEast.contains(next) || seaFloor.south().contains(next)) {
                newSouth.add(seaCucumber);
            } else {
                newSouth.add(next);
                movedCount++;
            }
        }

        return new SeaFloor(seaFloor.height(), seaFloor.width(), newEast, newSouth, movedCount != 0);
    }

    private static SeaFloor parseSeaFloor(List<String> input) {
        List<Coord> east = new ArrayList<>();
        List<Coord> south = new ArrayList<>();

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