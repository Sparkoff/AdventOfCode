package aoc2017;

import common.DayBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Day22 extends DayBase<Set<Day22.Point>, Integer, Integer> {

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


    enum State { CLEAN, WEAKENED, INFECTED, FLAGGED }
    enum Direction {
        UP(0),  LEFT(1), DOWN(2), RIGHT(3);

        private static final Direction[] a = {UP, LEFT, DOWN, RIGHT};
        private final int id;

        Direction(int id) {
            this.id = id;
        }

        public Direction turnLeft() {
            return a[(this.id + 1) % 4];
        }

        public Direction turnRight() {
            return a[(this.id + 3) % 4];
        }

        public Direction reverse() {
            return a[(this.id + 2) % 4];
        }

        public Direction facing(Direction turn) {
            return switch (turn) {
                case UP -> this;
                case LEFT -> turnLeft();
                case DOWN -> reverse();
                case RIGHT -> turnRight();
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
        Set<Point> initialInfected = this.getInput(Day22::parseGrid);
        Map<Point, State> grid = new HashMap<>();
        initialInfected.forEach(p -> grid.put(p, State.INFECTED));

        VirusCarrier virusCarrier = new VirusCarrier();
        int infectionBurst = 0;

        for (int i = 0; i < 1E7; i++) {
            Point currentLocation = virusCarrier.location();
            State currentState = grid.getOrDefault(currentLocation, State.CLEAN);
            Direction turn = Direction.UP;

            switch (currentState) {
                case CLEAN -> {
                    turn = Direction.LEFT;
                    grid.put(currentLocation, State.WEAKENED);
                }
                case WEAKENED -> {
                    grid.put(currentLocation, State.INFECTED);
                    infectionBurst++;
                }
                case INFECTED -> {
                    turn = Direction.RIGHT;
                    grid.put(currentLocation, State.FLAGGED);
                }
                case FLAGGED -> {
                    turn = Direction.DOWN;
                    grid.remove(currentLocation);
                }
            }
            virusCarrier = virusCarrier.move(turn); // Move forward
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
