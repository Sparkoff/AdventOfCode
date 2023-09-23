package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day15 extends DayBase<Day15.RiskMap, Integer , Integer> {

    public Day15() {
        super();
    }

    public Day15(List<String> input) {
        super(input);
    }

    enum MapMode {
        SMALL, LARGE
    }

    record RiskMap(List<Short> map, int size, MapMode mapMode) {

        public RiskMap(List<Short> map, int size) {
            this(map, size, MapMode.SMALL);
        }

        public short getRiskAt(int idx) {
            if (mapMode == MapMode.SMALL) return map.get(idx);

            int x_l = idx % size();
            int y_l = idx / size();
            int x_s = x_l % size;
            int y_s = y_l % size;
            int tile_x = x_l / size;
            int tile_y = y_l / size;
            int idx_s = y_s * size + x_s;

            short riskLevel_l = (short) (map.get(idx_s) + tile_x + tile_y);
            if (riskLevel_l > 9) {
                riskLevel_l -= 9;
            }

            return riskLevel_l;
        }

        @Override
        public int size() {
            return mapMode == MapMode.SMALL ? size : size * 5;
        }

        public int getMapSize() {
            return mapMode == MapMode.SMALL ? map.size() : map.size() * 25;
        }

        public static RiskMap asLarge(RiskMap riskMap) {
            return new RiskMap(riskMap.map, riskMap.size, MapMode.LARGE);
        }
    }

    record RiskStep(int idx, short riskLevel) {}


    @Override
    public Integer firstStar() {
        return findLowRiskPath(this.getInput(Day15::parseMap));
    }

    @Override
    public Integer secondStar() {
        return findLowRiskPath(RiskMap.asLarge(this.getInput(Day15::parseMap)));
    }

    private static Integer findLowRiskPath(RiskMap riskMap) {
        List<Short> pathMap = IntStream.range(0, riskMap.getMapSize())
                .mapToObj(i -> Short.MAX_VALUE)
                .collect(Collectors.toList());
        pathMap.set(0, (short) 0);

        List<RiskStep> remains = new ArrayList<>();
        remains.add(new RiskStep(0, (short) 0));

        while (!remains.isEmpty()) {
            RiskStep next = remains.remove(0);
            if (pathMap.get(next.idx) < next.riskLevel) continue;

            List<RiskStep> adjacents = getAdjacentIndexes(next.idx, riskMap)
                    .stream()
                    .map(idx -> new RiskStep(idx, (short) (next.riskLevel + riskMap.getRiskAt(idx))))
                    .filter(rs -> rs.riskLevel < pathMap.get(rs.idx))
                    .toList();

            for (RiskStep rs : adjacents) {
                pathMap.set(rs.idx, rs.riskLevel);
            }

            remains.addAll(adjacents);
        }

        return (int) pathMap.get(pathMap.size() - 1);
    }

    private static List<Integer> getAdjacentIndexes(int index, RiskMap riskMap) {
        List<Integer> adjacentIndexes = new ArrayList<>();
        int mapSize = riskMap.size();

        // left point : check current point is not on left edge
        if (index % mapSize > 0) {
            adjacentIndexes.add(index - 1);
        }
        // right point : check current point is not on right edge
        if (index % mapSize < mapSize - 1) {
            adjacentIndexes.add(index + 1);
        }

        // up point : check current point is not on up edge
        if ( index / mapSize > 0) {
            adjacentIndexes.add(index - mapSize);
        }
        // down point : check current point is not on down edge
        if ( index / mapSize < mapSize - 1) {
            adjacentIndexes.add(index + mapSize);
        }

        return adjacentIndexes;
    }

    private static RiskMap parseMap(List<String> input) {
        return new RiskMap(
                input.stream()
                        .map(l -> Arrays.stream(l.split(""))
                                .map(Short::parseShort)
                                .toList())
                        .flatMap(List::stream)
                        .toList(),
                input.size()
        );
    }
}