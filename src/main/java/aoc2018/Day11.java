package aoc2018;

import common.DayBase;
import common.PuzzleInput;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


public class Day11 extends DayBase<Integer, String, String> {

    public Day11() {
        super();
    }

    public Day11(List<String> input) {
        super(input);
    }

    record FuelCellGrid(List<List<Integer>> fuelCells) {
        public static int size() {
            return 300;
        }
        public static int lastIndexOnSize(int squareSize) {
            return 300 - squareSize + 1;
        }

        public int powerAt(int x, int y) {
            return fuelCells.get(y - 1).get(x - 1);
        }

        public int powerAt(int x, int y, int squareSize) {
            return IntStream.range(y - 1, y + squareSize - 1)
                    .map(yi -> fuelCells.get(yi)
                            .subList(x - 1, x + squareSize - 1)
                            .stream()
                            .mapToInt(i -> i)
                            .sum()
                    )
                    .sum();
        }
    }
    record Identifier(int x, int y, int s) {
        public String toString() {
            return String.format("%d,%d,%d", x, y, s);
        }
    }


    @Override
    public String firstStar() {
        Integer serialNumber = this.getInput(PuzzleInput::asInt);

        FuelCellGrid fuelCellGrid = buildFuelCellGrid(serialNumber);

        return IntStream.rangeClosed(1, FuelCellGrid.lastIndexOnSize(3))
                .mapToObj(y -> IntStream.rangeClosed(1, FuelCellGrid.lastIndexOnSize(3))
                        .mapToObj(x -> new AbstractMap.SimpleEntry<>(
                                String.format("%d,%d", x, y),
                                fuelCellGrid.powerAt(x, y, 3)
                        ))
                        .max(Comparator.comparingInt(Map.Entry::getValue))
                        .orElseThrow())
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow()
                .getKey();
    }

    @Override
    public String secondStar() {
        Integer serialNumber = this.getInput(PuzzleInput::asInt);

        FuelCellGrid fuelCellGrid = buildFuelCellGrid(serialNumber);

        Map<Identifier,Integer> powers = new HashMap<>();

        for (int squareSize = 1; squareSize <= FuelCellGrid.size(); squareSize++) {
            for (int x = 1; x <= FuelCellGrid.lastIndexOnSize(squareSize); x++) {
                for (int y = 1; y <= FuelCellGrid.lastIndexOnSize(squareSize); y++) {
                    int power;
                    Identifier current = new Identifier(x, y, squareSize);

                    if (squareSize == 1) {
                        power = fuelCellGrid.powerAt(x, y);
                    } else {
                        // ####   ###.   ....   ....   ...#   ....
                        // ####   ###.   .###   ....   ....   .##.
                        // #### = ###. + .### + .... + .... - .##.
                        // ####   ....   .###   #...   ....   ....
                        power = powers.get(new Identifier(x, y, squareSize - 1)) +
                                powers.get(new Identifier(x + 1, y + 1, squareSize - 1)) +
                                powers.get(new Identifier(x + squareSize - 1, y, 1)) +
                                powers.get(new Identifier(x, y + squareSize - 1, 1));
                        if (squareSize > 2) {
                            power -= powers.get(new Identifier(x + 1, y + 1, squareSize - 2));
                        }
                    }

                    powers.put(current, power);
                }
            }
        }

        return powers.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow()
                .getKey()
                .toString();
    }

    public static int fuelCellAt(int x, int y, int serialNumber) {
        int rackId = x + 10;
        int powerLevel = ((rackId * y) + serialNumber) * rackId;
        int hundred = (powerLevel / 100) - ((powerLevel / 1000) * 10);
        return hundred - 5;
    }
    private static FuelCellGrid buildFuelCellGrid(int serialNumber) {
        return new FuelCellGrid(IntStream.rangeClosed(1, FuelCellGrid.size())
                .mapToObj(y -> IntStream.rangeClosed(1, FuelCellGrid.size())
                        .mapToObj(x -> fuelCellAt(x, y, serialNumber))
                        .toList())
                .toList());
    }
}
