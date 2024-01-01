package aoc2017;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class Day3 extends DayBase<Integer, Integer, Integer> {

    public Day3() {
        super();
    }

    public Day3(List<String> input) {
        super(input);
    }

    record Coord(int x, int y) {
        public Stream<Coord> adjacentSquares() {
            List<Coord> adjacentSquares = new ArrayList<>();
            adjacentSquares.add(new Coord(x - 1, y - 1));
            adjacentSquares.add(new Coord(x, y - 1));
            adjacentSquares.add(new Coord(x + 1, y - 1));
            adjacentSquares.add(new Coord(x - 1, y));
            adjacentSquares.add(new Coord(x + 1, y));
            adjacentSquares.add(new Coord(x - 1, y + 1));
            adjacentSquares.add(new Coord(x, y + 1));
            adjacentSquares.add(new Coord(x + 1, y + 1));
            return adjacentSquares.stream();
        }
    }

    @Override
    public Integer firstStar() {
        Integer stopNumber = this.getInput(PuzzleInput::asInt);

        // search for last full square
        int lowSquare = 1;
        while (Math.pow(lowSquare + 2, 2) < stopNumber) {
            lowSquare += 2;
        }

        if (lowSquare > 1) {
            // value on cardinal points
            for (int i = 0; i < 4; i++) {
                int cardinal = (int) (Math.pow(lowSquare, 2) + 1 + lowSquare / 2 + i * (lowSquare + 1));
                if (Math.abs(stopNumber - cardinal) <= lowSquare / 2) {
                    return Math.abs(stopNumber - cardinal) + lowSquare / 2 + 1;
                }
            }
        }

        return 0;
    }

    @Override
    public Integer secondStar() {
        Integer stopNumber = this.getInput(PuzzleInput::asInt);

        Map<Coord,Integer> spirale = new HashMap<>();
        spirale.put(new Coord(0, 0), 1);

        int x = 0, y = 0;
        int dx = 1, dy = 0;
        int borderLength = 1;
        int currentBorder = 0;
        int currentValue = 1;
        while(currentValue < stopNumber) {
            x += dx;
            y += dy;
            currentBorder++;

            Coord currentCoord = new Coord(x, y);
            currentValue = currentCoord.adjacentSquares()
                    .mapToInt(c -> spirale.getOrDefault(c, 0))
                    .sum();

            if (currentValue > stopNumber) {
                return currentValue;
            }
            spirale.put(currentCoord, currentValue);

            if (currentBorder == borderLength) {
                // swap, no temp variable ^^
                dx -= dy;
                dy += dx;
                dx -= dy;

                currentBorder = 0;

                // increase square length on bottom border
                if (dy == 0) {
                    borderLength++;
                }
            }
        }

        return 0;
    }
}
