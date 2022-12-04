package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day19 extends DayBase<List<Day19.Probe>, Integer> {

    public Day19() {
        super();
    }

    public Day19(List<String> input) {
        super(input);
    }

    record Point(int x, int y, int z) {
        public Point(int x, int y) {
            this(x, y, 0);
        }
    }

    record Probe(int id, List<Point> beacons) {}

    @Override
    public Integer firstStar() {
        List<Probe> probes = this.getInput(Day19::parseScanners);
        return null;
    }

    @Override
    public Integer secondStar() {
        return null;
    }

    private static List<Probe> parseScanners(List<String> input) {
        List<String> extendedInput = new ArrayList<>(input);
        extendedInput.add("");

        List<Probe> probes = new ArrayList<>();

        int id = -1;
        List<Point> beacons = new ArrayList<>();
        for (String line : extendedInput) {
            if (line.contains("scanner")) {
                id = Integer.parseInt(Arrays.stream(line.split(" ")).toList().get(2));
            } else if (line.isEmpty()) {
                probes.add(new Probe(id, beacons));
                id = -1;
                beacons = new ArrayList<>();
            } else {
                List<Integer> p = Arrays.stream(line.split((",")))
                        .map(Integer::parseInt)
                        .toList();
                if (p.size() == 3) {
                    beacons.add(new Point(p.get(0), p.get(1), p.get(2)));
                } else {
                    beacons.add(new Point(p.get(0), p.get(1)));
                }
            }
        }

        return probes;
    }
}