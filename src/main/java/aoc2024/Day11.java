package aoc2024;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Day11 extends DayBase<Map<Long, Long>, Integer, Long> {

    public Day11() {
        super();
    }

    public Day11(List<String> input) {
        super(input);
    }

    record Entry(long stone, long count) {}


    @Override
    public Integer firstStar() {
        Map<Long, Long> stones = this.getInput(Day11::parseStones);

        // Don't care about the stone order, it doesn't have any impact on process
        // To simplify process, use a map of stone value/count
        for (int i = 0; i < 25; i++) {
            stones = stones.entrySet().stream()
                    .flatMap(e -> this.blink(e.getKey()).stream()
                            .map(s -> new Entry(s, e.getValue()))
                            .collect(Collectors.groupingBy(Entry::stone, Collectors.summingLong(Entry::count)))
                            .entrySet().stream()
                    )
                    .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));
        }

        return Math.toIntExact(stones.values().stream()
                .mapToLong(Long::longValue)
                .sum());
    }

    @Override
    public Long secondStar() {
        Map<Long, Long> stones = this.getInput(Day11::parseStones);

        // Don't care about the stone order, it doesn't have any impact on process
        // To simplify process, use a map of stone value/count
        for (int i = 0; i < 75; i++) {
            stones = stones.entrySet().stream()
                    .flatMap(e -> this.blink(e.getKey()).stream()
                            .map(s -> new Entry(s, e.getValue()))
                            .collect(Collectors.groupingBy(Entry::stone, Collectors.summingLong(Entry::count)))
                            .entrySet().stream()
                    )
                    .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));
        }

        return stones.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private List<Long> blink(long stone) {
        String value = String.valueOf(stone);

        if (stone == 0L) {
            return List.of(1L);
        } else if (value.length() % 2 == 0) {
            return List.of(
                    Long.parseLong(value.substring(0, value.length() / 2)),
                    Long.parseLong(value.substring(value.length() / 2))
            );
        } else {
            return List.of(stone * 2024L);
        }
    }


    private static Map<Long, Long> parseStones(List<String> input) {
        return Arrays.stream(PuzzleInput.asString(input).split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toMap(Function.identity(), e -> 1L));
    }
}
