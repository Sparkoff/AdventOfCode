package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;


public class Day3 extends DayBase<Day3.Schematics, Integer, Integer> {

    public Day3() {
        super();
    }

    public Day3(List<String> input) {
        super(input);
    }

    record Point(int x, int y) {
        public List<Point> getRegion() {
            return List.of(
                    this,
                    new Point(x - 1, y - 1),
                    new Point(x - 1, y),
                    new Point(x - 1, y + 1),
                    new Point(x, y - 1),
                    new Point(x, y),
                    new Point(x, y + 1),
                    new Point(x + 1, y - 1),
                    new Point(x + 1, y),
                    new Point(x + 1, y + 1)
            );
        }
    }
    record Number(int value, List<Point> region) {}
    record Symbol(char value, Point location) {}
    record Schematics(List<Number> numbers, List<Symbol> symbols) {
        public List<Point> symbolLocations() {
            return symbols.stream()
                    .map(Symbol::location)
                    .toList();
        }
    }


    @Override
    public Integer firstStar() {
        Schematics schematics = this.getInput(Day3::parseSchematics);

        List<Point> symbols = schematics.symbolLocations();
        return schematics.numbers().stream()
                .filter(n -> n.region().stream().anyMatch(symbols::contains))
                .mapToInt(Number::value)
                .sum();
    }

    @Override
    public Integer secondStar() {
        Schematics schematics = this.getInput(Day3::parseSchematics);

        return schematics.symbols().stream()
                .filter(s -> s.value() == '*')
                .map(Symbol::location)
                .map(s -> schematics.numbers().stream()
                        .filter(n -> n.region().contains(s))
                        .toList())
                .filter(n -> n.size() == 2)
                .mapToInt(n -> n.stream().mapToInt(Number::value).reduce(1, (a, b) -> a * b))
                .sum();
    }

    private static Schematics parseSchematics(List<String> input) {
        List<Number> numbers = new ArrayList<>();
        List<Symbol> symbols = new ArrayList<>();

        for (int y = 0; y < input.size(); y++) {
            Integer num = null;
            List<Point> numLoc = new ArrayList<>();

            for (int x = 0; x < input.get(y).length(); x++) {
                try {
                    int digit = Integer.parseInt(String.valueOf(input.get(y).charAt(x)));
                    if (num != null) {
                        num = num * 10 + digit;
                    } else {
                        num = digit;
                    }
                    numLoc.add(new Point(x, y));
                } catch (NumberFormatException e) {
                    if (num != null) {
                        numbers.add(createNumber(num, numLoc));
                        num = null;
                        numLoc = new ArrayList<>();
                    }
                    if (input.get(y).charAt(x) != '.') {
                        symbols.add(new Symbol(input.get(y).charAt(x), new Point(x, y)));
                    }
                }
            }

            if (num != null) {
                numbers.add(createNumber(num, numLoc));
            }
        }

        return new Schematics(numbers, symbols);
    }

    private static Number createNumber(Integer num, List<Point> numLoc) {
        return new Number(
                num,
                numLoc.stream()
                        .map(Point::getRegion)
                        .flatMap(List::stream)
                        .distinct()
                        .toList()
        );
    }
}
