package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Day8 extends DayBase<Day8.CityScan, Integer, Integer> {

    public Day8() {
        super();
    }

    public Day8(List<String> input) {
        super(input);
    }

    record Pt(int x, int y) {
        public static Pt vect(Pt a, Pt b) {
            return new Pt(b.x - a.x, b.y - a.y);
        }

        public Pt add(Pt vect) {
            return new Pt(this.x + vect.x, this.y + vect.y);
        }
        public Pt sub(Pt vect) {
            return new Pt(this.x - vect.x, this.y - vect.y);
        }
    }
    record CityScan(Map<Character, List<Pt>> antennaSet, int w, int h) {
        public boolean inBound(Pt pt) {
            return pt.x() >= 0 && pt.x() < w && pt.y() >= 0 && pt.y() < h;
        }
    }


    @Override
    public Integer firstStar() {
        CityScan cityScan = this.getInput(Day8::parseAntennas);

        Set<Pt> antinodes = new HashSet<>();

        for (List<Pt> antennas: cityScan.antennaSet().values()) {
            for (int i = 0; i < antennas.size() - 1; i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    Pt a = antennas.get(i);
                    Pt b = antennas.get(j);
                    Pt ab = Pt.vect(a, b);
                    antinodes.add(b.add(ab));
                    antinodes.add(a.sub(ab));
                }
            }
        }

        return Math.toIntExact(antinodes.stream()
                .filter(cityScan::inBound)
                .count());
    }

    @Override
    public Integer secondStar() {
        CityScan cityScan = this.getInput(Day8::parseAntennas);

        Set<Pt> antinodes = new HashSet<>();

        for (List<Pt> antennas: cityScan.antennaSet().values()) {
            for (int i = 0; i < antennas.size() - 1; i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    Pt a = antennas.get(i);
                    Pt b = antennas.get(j);
                    Pt ab = Pt.vect(a, b);

                    antinodes.add(a);
                    antinodes.add(b);

                    Pt next = b.add(ab);
                    while (cityScan.inBound(next)) {
                        antinodes.add(next);
                        next = next.add(ab);
                    }

                    next = a.sub(ab);
                    while (cityScan.inBound(next)) {
                        antinodes.add(next);
                        next = next.sub(ab);
                    }
                }
            }
        }

        return antinodes.size();
    }


    private static CityScan parseAntennas(List<String> input) {
        Map<Character, List<Pt>> antennas = new HashMap<>();

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);

                if (c != '.') {
                    if (!antennas.containsKey(c)) {
                        antennas.put(c, new ArrayList<>());
                    }

                    antennas.get(c).add(new Pt(x, y));
                }
            }
        }

        return new CityScan(antennas, input.get(0).length(), input.size());
    }
}
