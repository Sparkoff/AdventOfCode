package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Day10 extends DayBase<Map<Integer, List<Day10.Pt>>, Integer, Integer> {

    public Day10() {
        super();
    }

    public Day10(List<String> input) {
        super(input);
    }

    record Pt(int x, int y) {
        public List<Pt> adjacents() {
            return List.of(
                    new Pt(x, y + 1),
                    new Pt(x, y - 1),
                    new Pt(x + 1, y),
                    new Pt(x - 1, y)
            );
        }
    }
    record Step(int heat, List<Pt> pts) {}


    @Override
    public Integer firstStar() {
        Map<Integer, List<Pt>> heatMap = this.getInput(Day10::parseHeatMap);

        // init separately path from each 0-heat
        List<List<Pt>> steps = heatMap.get(0).stream()
                .map(List::of)
                .toList();

        int currentHeat = 0;
        while (currentHeat < 9) {
            currentHeat++;
            List<Pt> nextPts = heatMap.get(currentHeat);

            // update each path together, keeping separation on path init
            steps = steps.stream()
                    .map(pts -> pts.stream()
                            .flatMap(p -> p.adjacents().stream())
                            .distinct()
                            .filter(nextPts::contains)
                            .toList()
                    )
                    .toList();
        }
        return steps.stream()
                .mapToInt(List::size)
                .sum();
    }

    @Override
    public Integer secondStar() {
        Map<Integer, List<Pt>> heatMap = this.getInput(Day10::parseHeatMap);

        // init all paths together, each different location store count of path which reached it
        Map<Pt, Integer> next = heatMap.get(0).stream()
                .collect(Collectors.toMap(Function.identity(), p -> 1));

        int currentHeat = 0;
        while (currentHeat < 9) {
            currentHeat++;
            List<Pt> nextPts = heatMap.get(currentHeat);

            // from all location, search next point and keep track count of paths reaching it
            // group by points and sum paths
            next = next.entrySet().stream()
                    .flatMap(e -> e.getKey().adjacents().stream()
                            .filter(nextPts::contains)
                            .collect(Collectors.toMap(Function.identity(), p -> e.getValue()))
                            .entrySet().stream()
                    )
                    .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
        }

        return next.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }


    private static Map<Integer, List<Pt>> parseHeatMap(List<String> input) {
        Map<Integer, List<Pt>> heatMap = new HashMap<>();
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                int heat = input.get(y).charAt(x) - '0';

                if (!heatMap.containsKey(heat)) {
                    heatMap.put(heat, new ArrayList<>());
                }

                heatMap.get(heat).add(new Pt(x, y));
            }
        }

        return heatMap;
    }
}
