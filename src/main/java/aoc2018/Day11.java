package aoc2018;

import common.DayBase;
import common.PuzzleInput;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;


public class Day11 extends DayBase<Integer, String, String> {

    public Day11() {
        super();
    }

    public Day11(List<String> input) {
        super(input);
    }

    record SummedAreaTable(int[][] table) {
        public static SummedAreaTable fromSerialNumber(int serialNumber) {
            int gridSize = 301; // Use 301 for 1-based indexing (indices 1-300)
            int[][] sat = new int[gridSize][gridSize];
            for (int y = 1; y < gridSize; y++) {
                for (int x = 1; x < gridSize; x++) {
                    int power = fuelCellAt(x, y, serialNumber);
                    sat[y][x] = power + sat[y - 1][x] + sat[y][x - 1] - sat[y - 1][x - 1];
                }
            }
            return new SummedAreaTable(sat);
        }

        /**
         * Calculates the total power of a square of a given size with its top-left corner at (x, y).
         */
        public int getSquarePower(int x, int y, int squareSize) {
            int x1 = x - 1;
            int y1 = y - 1;
            int x2 = x + squareSize - 1;
            int y2 = y + squareSize - 1;
            return table[y2][x2] - table[y1][x2] - table[y2][x1] + table[y1][x1];
        }
    }

    record Identifier(int x, int y, int s) {
        @Override
        public String toString() {
            if (s == 0) { // For firstStar
                return String.format("%d,%d", x, y);
            }
            return String.format("%d,%d,%d", x, y, s);
        }
    }

    record PowerResult(Identifier id, int power) implements Comparable<PowerResult> {
        @Override
        public int compareTo(PowerResult other) {
            return Integer.compare(this.power, other.power);
        }
    }


    @Override
    public String firstStar() {
        Integer serialNumber = this.getInput(PuzzleInput::asInt);

        SummedAreaTable sat = SummedAreaTable.fromSerialNumber(serialNumber);
        int squareSize = 3;

        return IntStream.rangeClosed(1, 300 - squareSize + 1)
                .boxed()
                .flatMap(y -> IntStream.rangeClosed(1, 300 - squareSize + 1)
                        .mapToObj(x -> new PowerResult(new Identifier(x, y, 0), sat.getSquarePower(x, y, squareSize))))
                .max(Comparator.naturalOrder())
                .orElseThrow()
                .id()
                .toString();
    }

    @Override
    public String secondStar() {
        Integer serialNumber = this.getInput(PuzzleInput::asInt);

        SummedAreaTable sat = SummedAreaTable.fromSerialNumber(serialNumber);

        return IntStream.rangeClosed(1, 300)
                .parallel() // Parallelize the search across square sizes
                .mapToObj(squareSize -> {
                    return IntStream.rangeClosed(1, 300 - squareSize + 1)
                            .boxed()
                            .flatMap(y -> IntStream.rangeClosed(1, 300 - squareSize + 1)
                                    .mapToObj(x -> new PowerResult(new Identifier(x, y, squareSize), sat.getSquarePower(x, y, squareSize))))
                            .max(Comparator.naturalOrder())
                            .orElseThrow();
                })
                .max(Comparator.naturalOrder())
                .orElseThrow()
                .id()
                .toString();
    }


    public static int fuelCellAt(int x, int y, int serialNumber) {
        int rackId = x + 10;
        int powerLevel = ((rackId * y) + serialNumber) * rackId;
        int hundred = (powerLevel / 100) - ((powerLevel / 1000) * 10);
        return hundred - 5;
    }
}
