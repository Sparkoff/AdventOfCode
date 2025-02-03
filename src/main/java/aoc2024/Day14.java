package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day14 extends DayBase<List<Day14.Robot>, Integer, Integer> {
    private int width = 101;
    private int height = 103;

    public Day14() {
        super();
    }

    public Day14(List<String> input) {
        super(input);
    }
    public Day14(List<String> input, int w, int h) {
        super(input);
        width = w;
        height = h;
    }

    record Pt(int x, int y) {
        public static Pt fromSting(String s) {
            String[] c = s.split("=")[1].split(",");
            return new Pt(Integer.parseInt(c[0]), Integer.parseInt(c[1]));
        }

        public boolean inQuadrant(int width, int height) {
            return x != width / 2 && y != height / 2;
        }

        public int quadrantId(int width, int height) {
            return ((x < width / 2) ? 0 : 2) + ((y < height / 2) ? 0 : 1);
        }
    }
    record Robot(Pt p, Pt v) {
        public Pt move(int width, int height) {
            return move(width, height, 100);
        }
        public Pt move(int width, int height, int steps) {
            return new Pt(
                    Math.floorMod(p.x() + v.x() * steps, width),
                    Math.floorMod(p.y() + v.y() * steps, height)
            );
        }
    }
    record StepQuadrant(int i, long qi) {}


    @Override
    public Integer firstStar() {
        List<Robot> robots = this.getInput(Day14::parseRobots);

        return Math.toIntExact(robots.stream()
                .map(r -> r.move(width, height))
                .filter(p -> p.inQuadrant(width, height))
                .collect(Collectors.groupingBy(p -> p.quadrantId(width, height), Collectors.counting()))
                .values().stream()
                .reduce(1L, (a, c) -> a * c));
    }

    @Override
    public Integer secondStar() {
        List<Robot> robots = this.getInput(Day14::parseRobots);

        // robot cycle is limited by the map (x,y) dimension : max(101,103)
        Map<Integer, Long> quadrantByStep = IntStream.rangeClosed(1, Math.max(width, height))
                .mapToObj(i -> new StepQuadrant(i, robots.stream()
                        .map(r -> r.move(width, height, i))
                        .filter(p -> p.inQuadrant(width, height))
                        .collect(Collectors.groupingBy(p -> p.quadrantId(width, height), Collectors.counting()))
                        .values().stream()
                        .reduce(1L, (a, c) -> a * c)))
                .collect(Collectors.toMap(StepQuadrant::i, StepQuadrant::qi));

        // robots starts to regroup : quadrant factor will be lower as one would have more robot than others
        int stepCandidate = quadrantByStep.entrySet().stream()
                .min(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow()
                .getKey();

        // from candidate, move step by step by cycle to find lowest quadrant
        Map<Integer, Long> quadrantByCycle = IntStream.rangeClosed(1, Math.max(width, height))
                .mapToObj(i -> new StepQuadrant(i, robots.stream()
                        .map(r -> r.move(width, height, i * Math.min(width, height) + stepCandidate))
                        .filter(p -> p.inQuadrant(width, height))
                        .collect(Collectors.groupingBy(p -> p.quadrantId(width, height), Collectors.counting()))
                        .values().stream()
                        .reduce(1L, (a, c) -> a * c)))
                .collect(Collectors.toMap(StepQuadrant::i, StepQuadrant::qi));

        // robots starts to regroup : quadrant factor will be lower as one would have more robot than others
        int cycleCandidate = quadrantByCycle.entrySet().stream()
                .min(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow()
                .getKey();

        int eaterEggSec = cycleCandidate * Math.min(width, height) + stepCandidate;

        print(robots.stream()
                .map(r -> r.move(width, height, eaterEggSec))
                .toList());

        return eaterEggSec;
    }

    private void print(List<Pt> robots) {
        List<StringBuilder> map = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            StringBuilder s = new StringBuilder();
            s.append(".".repeat(Math.max(0, width)));
            map.add(s);
        }

        for (Pt pt : robots) {
            char current = map.get(pt.y()).charAt(pt.x());
            int count = current == '.' ? 1 : current - '0' + 1;
            map.get(pt.y()).replace(pt.x(), pt.x() + 1, String.valueOf(count));
        }

        map.forEach(System.out::println);
    }


    private static List<Robot> parseRobots(List<String> input) {
        return input.stream()
                .map(l -> l.split(" "))
                .map(l -> new Robot(Pt.fromSting(l[0]), Pt.fromSting(l[1])))
                .toList();
    }
}
