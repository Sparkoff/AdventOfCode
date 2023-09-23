package aoc2021;

import common.DayBase;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;


public class Day2 extends DayBase<List<Day2.Command>, Integer, Integer> {

    public Day2() {
        super();
    }

    public Day2(List<String> input) {
        super(input);
    }

    enum CommandType {
        FORWARD, DOWN, UP;

        public static CommandType fromString(String type) {
            return Arrays.stream(CommandType.values())
                    .filter(ct -> ct.name().equalsIgnoreCase(type))
                    .findFirst()
                    .orElseThrow();
        }
    }

    record Command(CommandType type, int value) {
        public Coordinate asCoordinate() {
            switch (this.type) {
                case FORWARD -> {
                    return new Coordinate(this.value(), 0);
                }

                case DOWN -> {
                    return new Coordinate(0, this.value());
                }

                case UP -> {
                    return new Coordinate(0, - this.value());
                }
            }
            throw new RuntimeException(String.format("Can't read command %s", this.type().toString()));
        }
    }

    record Coordinate(int position, int depth, int aim) {

        public Coordinate() {
            this(0, 0, 0);
        }

        public Coordinate(int position, int depth) {
            this(position, depth, 0);
        }

        public Coordinate cumulate(Coordinate other) {
            return new Coordinate(
                    this.position + other.position,
                    this.depth + other.depth
            );
        }

        public Coordinate cumulateWithAim(Coordinate other) {
            return new Coordinate(
                    this.position + other.position,
                    other.position > 0 ? this.depth + (this.aim * other.position) : this.depth,
                    this.aim + other.depth
            );
        }
    }

    @Override
    public Integer firstStar() {
        List<Command> commands = this.getInput(Day2::castToCommands);

        Coordinate coordinate = commands.stream()
                .map(Command::asCoordinate)
                .reduce(new Coordinate(), Coordinate::cumulate);
        return coordinate.position * coordinate.depth;
    }

    @Override
    public Integer secondStar() {
        List<Command> commands = this.getInput(Day2::castToCommands);

        Coordinate coordinate = commands.stream()
                .map(Command::asCoordinate)
                .reduce(new Coordinate(), Coordinate::cumulateWithAim);
        return coordinate.position * coordinate.depth;
    }

    private static List<Command> castToCommands(List<String> stringCommand) {
        return stringCommand.stream().map(c -> c.split(" "))
                .map(c -> new Command(CommandType.fromString(c[0]), parseInt(c[1])))
                .toList();
    }
}
