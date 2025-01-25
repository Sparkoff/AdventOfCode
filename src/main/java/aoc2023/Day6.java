package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day6 extends DayBase<List<Day6.Race>, Integer, Long> {

    public Day6() {
        super();
    }

    public Day6(List<String> input) {
        super(input);
    }
    
    record Race(int time, int distance) {}


    @Override
    public Integer firstStar() {
        List<Race> races = this.getInput(Day6::parseRace);

        return races.stream()
                .mapToInt(r -> {
                    int valid = 0;
                    for (int i = 0; i < r.time() / 2; i++) {
                        if ((r.time() - i) * i > r.distance()) {
                            valid = i;
                            break;
                        }
                    }
                    return (r.time() - 1) - (valid - 1) * 2;
                })
                .reduce(1, (a, b) -> a * b);
    }

    @Override
    public Long secondStar() {
        List<Race> races = this.getInput(Day6::parseRace);

        long time = concatInt(races.stream().map(Race::time).toList());
        long distance = concatInt(races.stream().map(Race::distance).toList());

        long min = 0;
        long max = Long.MAX_VALUE;
        long current = 0;

        while (min != max - 1) {
            if (current == 0) {
                current = 1;
            } else if (max == Long.MAX_VALUE) {
                current *= 2;
            } else {
                current = min + (max - min) / 2;
            }

            if ((time - current) * current > distance) {
                max = current;
            } else {
                min = current;
            }
        }

        return (time - 1) - min * 2;
    }

    private static long concatInt(List<Integer> values) {
        return Long.parseLong(values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("")));
    }


    private static List<Race> parseRace(List<String> input) {
        List<Integer> times = extractInt(input.get(0));
        List<Integer> distance = extractInt(input.get(1));

        List<Race> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distance.get(i)));
        }

        return races;
    }

    private static List<Integer> extractInt(String line) {
        return Arrays.stream(line.split(":")[1].split(" "))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
    }
}
