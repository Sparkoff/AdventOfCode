package aoc2017;

import common.DayBase;
import common.PuzzleInput;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day14 extends DayBase<String, Integer, Integer> {

    public Day14() {
        super();
    }

    public Day14(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        String hash = this.getInput(PuzzleInput::asString);

        return computeDiskState(hash).stream()
                .mapToInt(row -> (int) row.chars().filter(c -> c == '1').count())
                .sum();
    }

    @Override
    public Integer secondStar() {
        String hash = this.getInput(PuzzleInput::asString);
        List<String> diskState = computeDiskState(hash);

        Set<String> visited = new HashSet<>();
        int regionCount = 0;

        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 128; x++) {
                String key = x + "," + y;
                if (diskState.get(y).charAt(x) == '1' && !visited.contains(key)) {
                    regionCount++;
                    exploreRegion(x, y, diskState, visited);
                }
            }
        }

        return regionCount;
    }

    private void exploreRegion(int x, int y, List<String> disk, Set<String> visited) {
        if (x < 0 || x >= 128 || y < 0 || y >= 128) return;

        String key = x + "," + y;
        if (visited.contains(key) || disk.get(y).charAt(x) == '0') return;

        visited.add(key);

        exploreRegion(x + 1, y, disk, visited);
        exploreRegion(x - 1, y, disk, visited);
        exploreRegion(x, y + 1, disk, visited);
        exploreRegion(x, y - 1, disk, visited);
    }

    private static List<String> computeDiskState(String hash) {
        return IntStream.range(0, 128)
                .boxed()
                .map(i -> Day10.generateKnotHash(hash + "-" + i)) // Assuming Day10 provides the hex hash
                .map(Day14::hexToBinaryRow)
                .toList();
    }

    private static String hexToBinaryRow(String hexString) {
        return hexString.chars()
                .mapToObj(c -> (char) c)
                .map(c -> Integer.parseInt(String.valueOf(c), 16))
                .map(Integer::toBinaryString)
                .map(bin -> String.format("%4s", bin).replace(' ', '0'))
                .collect(Collectors.joining());
    }
}
