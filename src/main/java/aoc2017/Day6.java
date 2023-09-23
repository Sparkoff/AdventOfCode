package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Day6 extends DayBase<List<Integer>, Integer , Integer> {

    public Day6() {
        super();
    }

    public Day6(List<String> input) {
        super(input);
    }

    record DebugData(int loopSize, int totalCycles) {}

    @Override
    public Integer firstStar() {
        List<Integer> memoryBanks = this.getInput(Day6::parseMemoryBanks);

        return debug(memoryBanks).totalCycles();
    }

    @Override
    public Integer secondStar() {
        List<Integer> memoryBanks = this.getInput(Day6::parseMemoryBanks);

        return debug(memoryBanks).loopSize();
    }

    static DebugData debug(List<Integer> memoryBanks) {
        memoryBanks = new ArrayList<>(memoryBanks);

        List<String> seen = new ArrayList<>();
        seen.add(memToString(memoryBanks));
        do {
            int topValue = Collections.max(memoryBanks);
            int topIdx = memoryBanks.indexOf(topValue);

            memoryBanks.set(topIdx, 0);
            for (int i = 0; i < topValue; i++) {
                int idx = (topIdx + i + 1) % memoryBanks.size();
                int currentValue = memoryBanks.get(idx);
                memoryBanks.set(idx, currentValue + 1);
            }

            seen.add(memToString(memoryBanks));
        } while (seen.size() == seen.stream().distinct().count());

        int loopSize = seen.size() - seen.indexOf(seen.get(seen.size() - 1)) - 1;
        return new DebugData(loopSize, seen.size() - 1);
    }

    static String memToString(List<Integer> memoryBanks) {
        return memoryBanks.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(":"));
    }

    private static List<Integer> parseMemoryBanks(List<String> input) {
        return Arrays.stream(input.get(0).split("\\s+"))
                .map(Integer::parseInt)
                .toList();
    }
}
