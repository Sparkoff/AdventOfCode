package aoc2017;

import common.DayBase;

import java.util.List;


public class Day13 extends DayBase<List<Day13.Scanner>, Integer , Integer> {

    public Day13() {
        super();
    }

    public Day13(List<String> input) {
        super(input);
    }

    record Scanner(int range, int depth) {
        public boolean getCaught(int delay) {
            return (delay + range) % (2 * (depth - 1)) == 0;
        }
        public int severity() {
            return range * depth;
        }
    }

    @Override
    public Integer firstStar() {
        List<Day13.Scanner> firewall = this.getInput(Day13::parseFirewall);

        return firewall.stream()
                .mapToInt(s -> s.getCaught(0) ? s.severity() : 0)
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<Day13.Scanner> firewall = this.getInput(Day13::parseFirewall);

        int delay = 1;
        while (true) {
            boolean caught = false;
            for (Scanner s : firewall) {
                caught = s.getCaught(delay);
                if (caught) {
                    delay++;
                    break;
                }
            }
            if (!caught) {
                return delay;
            }
        }
    }

    private static List<Day13.Scanner> parseFirewall(List<String> input) {
        return input.stream()
                .map(l -> l.split(": "))
                .map(l -> new Scanner(Integer.parseInt(l[0]), Integer.parseInt(l[1])))
                .toList();
    }
}
