package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day3 extends DayBase<List<Day3.Square>, Integer, Integer> {

    public Day3() {
        super();
    }

    public Day3(List<String> input) {
        super(input);
    }

    record Point(int x, int y) {}
    record Square(int id, Point origin, int width, int height) {
        public List<Point> getCells() {
            List<Point> cells = new ArrayList<>();
            for (int x = origin.x; x < origin.x + width; x++) {
                for (int y = origin.y; y < origin.y + height; y++) {
                    cells.add(new Point(x, y));
                }
            }
            return cells;
        }

        public boolean hasIntersectionWith(Square other) {
            return this.edges().stream().anyMatch(other::isInSquare) ||
                    other.edges().stream().anyMatch(this::isInSquare);
        }
        private boolean isInSquare(Point p) {
            return p.x >= origin.x && p.x < origin.x + width &&
                    p.y >= origin.y && p.y < origin.y + height;
        }
        private List<Point> edges() {
            return List.of(
                    origin,
                    new Point(origin.x + width - 1, origin.y),
                    new Point(origin.x, origin.y + height - 1),
                    new Point(origin.x + width - 1, origin.y + height - 1),
                    new Point(origin.x + width / 2, origin.y + height / 2)
            );
        }
    }

    @Override
    public Integer firstStar() {
        List<Square> squares = this.getInput(Day3::parseSquares);

        Set<Integer> encountered = new HashSet<>();
        Set<Integer> duplicated = new HashSet<>();

        for (Square square : squares) {
            for (Point p : square.getCells()) {
                int loc = p.x() * 1000 + p.y();
                if (!encountered.add(loc)) {
                    duplicated.add(loc);
                }
            }
        }

        return duplicated.size();
    }

    @Override
    public Integer secondStar() {
        List<Square> squares = this.getInput(Day3::parseSquares);

        for (int i = 0; i < squares.size(); i++) {
            for (int j = 0; j < squares.size(); j++) {
                if (i == j) {
                    if (i == squares.size() - 1) {
                        return squares.get(i).id();
                    }
                    continue;
                }
                if (squares.get(i).hasIntersectionWith(squares.get(j))) {
                    break;
                }
                if (j == squares.size() - 1) {
                    return squares.get(i).id();
                }
            }
        }
        throw new RuntimeException("All squares have intersections");
    }

    private static List<Square> parseSquares(List<String> input) {
        Pattern p = Pattern.compile("#(\\d+)\\s@\\s(\\d+),(\\d+):\\s(\\d+)x(\\d+)");

        return input.stream()
                .map(l -> {
                    Matcher m = p.matcher(l);
                    if (!m.find()) throw new RuntimeException("Regex not matching for : " + l);
                    return m;
                })
                .map(m -> new Square(
                        Integer.parseInt(m.group(1)),
                        new Point(
                                Integer.parseInt(m.group(2)),
                                Integer.parseInt(m.group(3))
                        ),
                        Integer.parseInt(m.group(4)),
                        Integer.parseInt(m.group(5))
                ))
                .toList();
    }
}
