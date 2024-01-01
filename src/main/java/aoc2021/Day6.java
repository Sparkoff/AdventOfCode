package aoc2021;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day6 extends DayBase<Map<Integer, Long>, Long, Long> {

    public Day6() {
        super();
    }

    public Day6(List<String> input) {
        super(input);
    }

    @Override
    public Long firstStar() {
        Map<Integer, Long> population = this.getInput(Day6::parsePopulation);

        return simulate(population, 80).values()
                .stream()
                .reduce(0L, Long::sum);
    }

    @Override
    public Long secondStar() {
        Map<Integer, Long> population = this.getInput(Day6::parsePopulation);

        return simulate(population, 256).values()
                .stream()
                .reduce(0L, Long::sum);
    }

    private static Map<Integer, Long> simulate(Map<Integer, Long> population, int days) {
        for (int i = 0; i < days; i++) {
            Map<Integer, Long> newPopulation = IntStream.rangeClosed(0, 8)
                    .boxed()
                    .collect(Collectors.toMap(Function.identity(), e -> 0L));

            // handle new generation and timer reset
            newPopulation.put(6, population.get(0));
            newPopulation.put(8, population.get(0));

            // run the others
            for (int j = 1; j <= 8; j++) {
                newPopulation.merge(j - 1, population.get(j), Long::sum);
            }

            population = newPopulation;
        }
        return population;
    }

    private static Map<Integer, Long> parsePopulation(List<String> input) {
        Map<Integer, Long> initState = Arrays.stream(PuzzleInput.asString(input).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return IntStream.rangeClosed(0, 8)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> initState.getOrDefault(i, 0L)));
    }
}
