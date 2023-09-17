package aoc2017;

import common.DayBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day19 extends DayBase<Map<Day19.Point, String>, String> {

    public Day19() {
        super();
    }

    public Day19(List<String> input) {
        super(input);
    }

    record Point(int x, int y) {
        public Point next(Direction dir) {
            return switch (dir) {
                case UP -> new Point(x, y - 1);
                case DOWN -> new Point(x, y + 1);
                case LEFT -> new Point(x - 1, y);
                case RIGHT -> new Point(x + 1, y);
            };
        }
    }
    record Explore(String word, int length) {}
    enum Direction {UP, DOWN, LEFT, RIGHT}

    @Override
    public String firstStar() {
        Map<Day19.Point, String> map = this.getInput(Day19::parseMap);

        return explorePath(map).word();
    }

    @Override
    public String secondStar() {
        Map<Day19.Point, String> map = this.getInput(Day19::parseMap);

        return String.valueOf(explorePath(map).length());
    }

    private static Explore explorePath(Map<Day19.Point, String> map) {
        StringBuilder word = new StringBuilder();
        int steps = 1;
        Direction dir = Direction.DOWN;
        Point pt = map.entrySet().stream()
                .filter(e -> e.getKey().y() == 0)
                .filter(e -> e.getValue().equals("|"))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();

        while (true) {
            String symbol = map.getOrDefault(pt, " ");
            if (symbol.equals("+")) {
                if (dir != Direction.DOWN && map.containsKey(pt.next(Direction.UP))) {
                    dir = Direction.UP;
                } else if (dir != Direction.UP && map.containsKey(pt.next(Direction.DOWN))) {
                    dir = Direction.DOWN;
                } else if (dir != Direction.LEFT && map.containsKey(pt.next(Direction.RIGHT))) {
                    dir = Direction.RIGHT;
                } else if (dir != Direction.RIGHT && map.containsKey(pt.next(Direction.LEFT))) {
                    dir = Direction.LEFT;
                }
            } else if (symbol.matches("[A-Z]")) {
                word.append(symbol);
            } else if (symbol.equals(" ")) {
                // ended
                steps--;
                break;
            }

            pt = pt.next(dir);
            steps++;
        }
        
        return new Explore(word.toString(), steps);
    }

    private static Map<Point, String> parseMap(List<String> input) {
        Map<Point, String> map = new HashMap<>();

        for (int y = 0; y < input.size(); y++) {
            String[] line = input.get(y).split("");
            for (int x = 0; x < line.length; x++) {
                if (!line[x].equals(" ")) {
                    map.put(new Point(x, y), line[x]);
                }
            }
        }

        return map;
    }
}
