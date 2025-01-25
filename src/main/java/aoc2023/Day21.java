package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day21 extends DayBase<Day21.Garden, Integer, Long> {
    private int stepOverride = 0;

    public Day21() {
        super();
    }

    public Day21(List<String> input, int stepOverride) {
        super(input);
        this.stepOverride = stepOverride;
    }

    record Pt(int x, int y) {
        public List<Pt> adjacent() {
            return List.of(
                    new Pt(x + 1, y),
                    new Pt(x - 1, y),
                    new Pt(x, y + 1),
                    new Pt(x, y - 1)
            );
        }
    }
    record Garden(List<String> map, int size, Pt origin) {
        public boolean isIn(Pt pt) {
            return pt.x() >= 0 && pt.y() >= 0
                    && pt.x() < size && pt.y() < size;
        }
    }


    @Override
    public Integer firstStar() {
        Garden garden = this.getInput(Day21::parseGarden);

        int steps = stepOverride != 0 ? stepOverride : 64;

        return (int) exploreMap(garden).values().stream()
                .filter(d -> d <= steps && d % 2 == steps % 2)
                .count();
    }

    @Override
    public Long secondStar() {
        Garden garden = this.getInput(Day21::parseGarden);

        Map<Pt, Integer> explored = exploreMap(garden);

        int distanceToEdge = garden.size() / 2;
        int n = ((stepOverride != 0 ? stepOverride : 26501365) - distanceToEdge) / garden.size();

        long oddSquare = explored.values().stream().filter(d -> d % 2 == 1).count();
        long oddCorners = explored.values().stream()
                .filter(d -> d > distanceToEdge && d % 2 == 1)
                .count();

        long evenSquare = explored.values().stream().filter(d -> d % 2 == 0).count();
        long evenCorners = explored.values().stream()
                .filter(d -> d > distanceToEdge && d % 2 == 0)
                .count();

        return ((long) Math.pow(n + 1, 2) * oddSquare)
                + ((long) Math.pow(n, 2) * evenSquare)
                - ((n + 1) * oddCorners)
                + (n * evenCorners);
    }

    private Map<Pt, Integer> exploreMap(Garden garden) {
        List<List<Pt>> reached = new ArrayList<>();
        reached.add(List.of(garden.origin()));

        int step = 1;
        while (!reached.get(reached.size() - 1).isEmpty()) {
            List<Pt> wave = reached.get(step - 1).stream()
                    .map(Pt::adjacent)
                    .flatMap(List::stream)
                    .distinct()
                    .toList();

            // remove visited locations
            if (step - 2 >= 0) {
                int finalStep = step;
                wave = wave.stream()
                        .filter(pt -> !reached.get(finalStep - 2).contains(pt))
                        .toList();
            }

            // remove unreachable locations
            wave = wave.stream()
                    .filter(garden::isIn)
                    .toList();

            // remove rocks
            wave = wave.stream()
                    .filter(pt -> garden.map().get(pt.y()).charAt(pt.x()) != '#')
                    .toList();

            reached.add(wave);
            step++;
        }

        return IntStream.range(0, reached.size())
                .mapToObj(i -> reached.get(i).stream().collect(Collectors.toMap(pt -> pt, pt -> i)))
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private static Garden parseGarden(List<String> input) {
        for (int y = 0; y < input.size(); y++) {
            int xo = input.get(y).indexOf('S');
            if (xo > 0) {
                return new Garden(input, input.size(), new Pt(xo, y));
            }
        }
        throw new RuntimeException("Missing origin");
    }
}
