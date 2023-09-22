package aoc2017;

import common.DayBase;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Day22 extends DayBase<Set<Day22.Point>, Integer> {

    public Day22() {
        super();
    }

    public Day22(List<String> input) {
        super(input);
    }

    record Point(int x, int y) {
        public Point() {
            this(0, 0);
        }
    }

    enum Direction {
        UP(0),  LEFT(1), DOWN(2), RIGHT(3);

        private final int id;

        Direction(int id) {
            this.id = id;
        }

        private static Direction getDirectionById(int id) {
            for (Direction d : values()) {
                if (d.id == id) {
                    return d;
                }
            }
            return null;
        }

        public Direction facing(Direction turn) {
            return switch (turn) {
                case UP -> this;
                case LEFT -> getDirectionById((this.id + 1) % 4);
                case DOWN -> getDirectionById((this.id + 2) % 4);
                case RIGHT -> getDirectionById((this.id + 3) % 4);
            };
        }
    }
    record VirusCarrier(Point location, Direction direction) {
        public VirusCarrier() {
            this(new Point(), Direction.UP);
        }

        public VirusCarrier move(Direction turn) {
            Direction newDirection = direction.facing(turn);

            return switch (newDirection) {
                case UP -> new VirusCarrier(new Point(location.x, location.y + 1), newDirection);
                case LEFT -> new VirusCarrier(new Point(location.x - 1, location.y), newDirection);
                case DOWN -> new VirusCarrier(new Point(location.x, location.y - 1), newDirection);
                case RIGHT -> new VirusCarrier(new Point(location.x + 1, location.y), newDirection);
            };
        }
    }

    @Override
    public Integer firstStar() {
        Set<Point> grid = new HashSet<>(this.getInput(Day22::parseGrid));

        VirusCarrier virusCarrier = new VirusCarrier();
        int infectionBurst = 0;
        for (int i = 0; i < 1E4; i++) {
            Direction turn;
            if (grid.contains(virusCarrier.location)) {
                turn = Direction.RIGHT;
                grid.remove(virusCarrier.location);
            } else {
                turn = Direction.LEFT;
                grid.add(virusCarrier.location);
                infectionBurst++;
            }
            virusCarrier = virusCarrier.move(turn);
        }

        return infectionBurst;
    }

    @Override
    public Integer secondStar() {
        Set<Point> infected = new HashSet<>(this.getInput(Day22::parseGrid));
        Set<Point> weakened = new HashSet<>();
        Set<Point> flagged = new HashSet<>();

        VirusCarrier virusCarrier = new VirusCarrier();
        int infectionBurst = 0;
        for (int i = 0; i < 1E7; i++) {
            Direction turn;
            if (weakened.contains(virusCarrier.location)) {
                turn = Direction.UP;
                weakened.remove(virusCarrier.location);
                infected.add(virusCarrier.location);
                infectionBurst++;
            } else if (infected.contains(virusCarrier.location)) {
                turn = Direction.RIGHT;
                infected.remove(virusCarrier.location);
                flagged.add(virusCarrier.location);
            } else if (flagged.contains(virusCarrier.location)) {
                turn = Direction.DOWN;
                flagged.remove(virusCarrier.location);
            } else {
                turn = Direction.LEFT;
                weakened.add(virusCarrier.location);
            }
            virusCarrier = virusCarrier.move(turn);
        }

        return infectionBurst;
    }

    private static Set<Point> parseGrid(List<String> input) {
        int extreme = (input.size() - 1) / 2;

        Set<Point> infected = new HashSet<>();
        for (int y = extreme; y >= -extreme; y--) {
            for (int x = -extreme; x <= extreme; x++) {
                if (input.get(extreme - y).charAt(x + extreme) == '#') {
                    infected.add(new Point(x, y));
                }
            }
        }

        return Collections.unmodifiableSet(infected);
    }
}
