package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;


public class Day18 extends DayBase<List<Day18.Dig>, Integer, Long> {

    public Day18() {
        super();
    }

    public Day18(List<String> input) {
        super(input);
    }

    enum Dir { UP, DOWN, LEFT, RIGHT }
    record Pt(long x, long y) {}
    record Dig(Dir dir, int distance, String color) {
        public Dig fromColor() {
            int dist = Integer.parseInt(color.substring(2, 7), 16);
            Dir ori = switch (color.substring(7, 8)) {
                case "0" -> Dir.RIGHT;
                case "1" -> Dir.DOWN;
                case "2" -> Dir.LEFT;
                case "3" -> Dir.UP;
                default -> throw new IllegalStateException("Unexpected value: " + color.substring(6, 7));
            };
            return new Dig(ori, dist, "");
        }
    }


    @Override
    public Integer firstStar() {
        List<Dig> digPlan = this.getInput(Day18::parseDigPlan);

        return Math.toIntExact(area(digPlan));
    }

    @Override
    public Long secondStar() {
        List<Dig> digPlan = this.getInput(Day18::parseDigPlan);

        List<Dig> coloredDigPlan = digPlan.stream()
                .map(Dig::fromColor)
                .toList();

        return area(coloredDigPlan);
    }

    private long area(List<Dig> digPlan) {
        long x = 0;
        long y = 0;
        List<Pt> corners = new ArrayList<>();
        long perimeter = 0;

        for (Dig dig : digPlan) {
            switch (dig.dir()) {
                case UP:
                    y -= dig.distance();
                    break;
                case DOWN:
                    y += dig.distance();
                    break;
                case LEFT:
                    x -= dig.distance();
                    break;
                case RIGHT:
                    x += dig.distance();
                    break;
            }
            corners.add(new Pt(x, y));
            perimeter += dig.distance();
        }

        long area = 0;
        for (int i = 0; i < corners.size() - 1; i++) {
            area += corners.get(i).x() * corners.get(i + 1).y() - corners.get(i + 1).x() * corners.get(i).y();
        }
        area += perimeter;

        return (area / 2) + 1;
    }


    private static List<Dig> parseDigPlan(List<String> input) {
        return input.stream()
                .map(l -> l.split(" "))
                .map(l -> {
                    Dir dir = switch (l[0]) {
                        case "U" -> Dir.UP;
                        case "D" -> Dir.DOWN;
                        case "L" -> Dir.LEFT;
                        case "R" -> Dir.RIGHT;
                        default -> throw new RuntimeException("unexpected direction " + l[0]);
                    };
                    return new Dig(dir, Integer.parseInt(l[1]), l[2]);
                })
                .toList();
    }
}
