package aoc2017;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day14 extends DayBase<String, Integer> {

    public Day14() {
        super();
    }

    public Day14(List<String> input) {
        super(input);
    }

    record Square(int x, int y) {
        public boolean isAdjacent(Square other) {
            return (this.x == other.x && (this.y == other.y - 1 || this.y == other.y + 1)) ||
                    ((this.x == other.x - 1 || this.x == other.x + 1) && this.y == other.y);
        }
    }

    @Override
    public Integer firstStar() {
        String hash = this.getInput(PuzzleInput::asString);

        return Arrays.stream(String.join("", computeDiskState(hash)).split(""))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    @Override
    public Integer secondStar() {
        String hash = this.getInput(PuzzleInput::asString);

        List<Square> used = getUsedSquares(hash);

        List<Square> currentRegion = new ArrayList<>();
        int regionCount = 0;
        while (!used.isEmpty()) {
            if (currentRegion.isEmpty()) {
                currentRegion.add(used.remove(0));
            }

            boolean keepExploring = false;
            for (int i = 0; i < used.size(); i++) {
                Square current = used.get(i);
                if (currentRegion.stream().anyMatch(s -> s.isAdjacent(current))) {
                    currentRegion.add(used.remove(i));
                    i--;
                    keepExploring = true;
                }
            }

            if (!keepExploring) {
                regionCount++;
                currentRegion = new ArrayList<>();
            }
        }

        return regionCount;
    }

    private static List<String> computeDiskState(String hash) {
        return IntStream.range(0, 128)
                .boxed()
                .map(i -> Day10.generateKnotHash(hash + "-" + i))
                .map(l -> Arrays.stream(l.split(""))
                        .map(hex -> String.format("%4s", Integer.toBinaryString(Integer.parseInt(hex, 16))).replace(' ', '0'))
                        .collect(Collectors.joining(""))
                )
                .toList();
    }

    private static List<Square> getUsedSquares(String hash) {
        List<String> diskState = computeDiskState(hash);
        List<Square> used = new ArrayList<>();

        for (int j = 0; j < diskState.size(); j++) {
            List<String> squares = List.of(diskState.get(j).split(""));
            for (int i = 0; i < squares.size(); i++) {
                if (Objects.equals(squares.get(i), "1")) {
                    used.add(new Square(i, j));
                }
            }
        }

        return used;
    }
}
