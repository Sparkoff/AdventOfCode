package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Day11 extends DayBase<Day11.Universe, Integer, Long> {
    private int scaleOverride = 0;

    public Day11() {
        super();
    }

    public Day11(List<String> input) {
        super(input);
    }

    public Day11(List<String> input, int expansionFactor) {
        super(input);
        this.scaleOverride = expansionFactor;
    }

    record Galaxy(long x, long y) {
        Galaxy moveX(long expandedX, long scale) {
            if (expandedX < x) {
                return new Galaxy(x + scale - 1, y);
            }
            return this;
        }
        Galaxy moveY(long expandedY, long scale) {
            if (expandedY < y) {
                return new Galaxy(x, y + scale - 1);
            }
            return this;
        }
        long manhattan(Galaxy other) {
            return Math.abs(other.x - this.x) + Math.abs(other.y - this.y);
        }
    }
    record Universe(List<Galaxy> galaxies) {
        long shorterLength() {
            return galaxies.stream()
                    .flatMap(g1 -> galaxies.stream().map(g1::manhattan))
                    .mapToLong(Long::longValue)
                    .sum() / 2;
        }
    }


    @Override
    public Integer firstStar() {
        Universe universe = this.getInput(Day11::parseUniverse);

        universe = expandN(universe, 2);

        return Math.toIntExact(universe.shorterLength());
    }

    @Override
    public Long secondStar() {
        Universe universe = this.getInput(Day11::parseUniverse);

        if (scaleOverride != 0) {
            universe = expandN(universe, scaleOverride);
        } else {
            universe = expandN(universe, (int) 1E6);
        }

        return universe.shorterLength();
    }

    private Universe expandN(Universe universe, int scale) {
        List<Galaxy> expandedGalaxies = universe.galaxies().stream().toList();

        List<Long> xs = universe.galaxies().stream()
                .map(Galaxy::x)
                .distinct()
                .toList();

        for (long x = Collections.max(xs); x > 0; x--) {
            if (!xs.contains(x)) {
                long finalX = x;
                expandedGalaxies = expandedGalaxies.stream()
                        .map(g -> g.moveX(finalX, scale))
                        .toList();
            }
        }

        List<Long> ys = universe.galaxies().stream()
                .map(Galaxy::y)
                .distinct()
                .toList();

        for (long y = Collections.max(ys); y > 0; y--) {
            if (!ys.contains(y)) {
                long finalY = y;
                expandedGalaxies = expandedGalaxies.stream()
                        .map(g -> g.moveY(finalY, scale))
                        .toList();
            }
        }

        return new Universe(expandedGalaxies);
    }


    private static Universe parseUniverse(List<String> input) {
        List<List<String>> map = input.stream()
                .map(l -> Arrays.stream(l.split(""))
                        .map(String::valueOf)
                        .toList())
                .toList();

        List<Galaxy> galaxies = new ArrayList<>();
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x).equals("#")) {
                    galaxies.add(new Galaxy(x, y));
                }
            }
        }

        return new Universe(galaxies);
    }
}
