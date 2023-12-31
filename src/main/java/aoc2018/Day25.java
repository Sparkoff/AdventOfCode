package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day25 extends DayBase<List<Day25.SpaceTime>, Integer, Integer> {

    public Day25() {
        super();
    }

    public Day25(List<String> input) {
        super(input);
    }

    record SpaceTime(int x, int y, int z, int t) {
        public int manhattan(SpaceTime other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z) + Math.abs(t - other.t);
        }
    }


    @Override
    public Integer firstStar() {
        List<SpaceTime> stars = this.getInput(Day25::parseStars);

        int constellationCount = 0;
        while (!stars.isEmpty()) {
            List<SpaceTime> constellation = new ArrayList<>();
            constellation.add(stars.remove(0));
            for (int i = 0; i < stars.size(); i++) {
                int finalI = i;
                if (constellation.stream().anyMatch(s -> s.manhattan(stars.get(finalI)) <= 3)) {
                    constellation.add(stars.remove(i));
                    i = -1;
                }
            }

            constellationCount++;
        }

        return constellationCount;
    }

    @Override
    public Integer secondStar() {
        return 2018;
    }

    private static List<SpaceTime> parseStars(List<String> input) {
        return input.stream()
                .map(l -> Arrays.stream(l.split(",")).map(Integer::parseInt).toList())
                .map(l -> new SpaceTime(l.get(0), l.get(1), l.get(2), l.get(3)))
                .collect(Collectors.toList());
    }
}
