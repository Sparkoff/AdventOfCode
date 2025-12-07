package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day6 extends DayBase<List<Integer>, Integer, Integer> {

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

        Map<String, Integer> seen = new HashMap<>();
        int cycles = 0;

        while (!seen.containsKey(memToString(memoryBanks))) {
            seen.put(memToString(memoryBanks), cycles);

            int topValue = Collections.max(memoryBanks);
            int topIdx = memoryBanks.indexOf(topValue);

            memoryBanks.set(topIdx, 0);
            for (int i = 0; i < topValue; i++) {
                int idx = (topIdx + i + 1) % memoryBanks.size();
                memoryBanks.set(idx, memoryBanks.get(idx) + 1);
            }
            cycles++;
        }

        int loopSize = cycles - seen.get(memToString(memoryBanks));
        return new DebugData(loopSize, cycles);
    }

    static String memToString(List<Integer> memoryBanks) {
        return memoryBanks.toString();
    }

    private static List<Integer> parseMemoryBanks(List<String> input) {
        return Arrays.stream(input.get(0).split("\\s+"))
                .map(Integer::parseInt)
                .toList();
    }
}
