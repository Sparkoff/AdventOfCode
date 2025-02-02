package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class Day13 extends DayBase<List<Day13.Machine>, Integer, Long> {

    public Day13() {
        super();
    }

    public Day13(List<String> input) {
        super(input);
    }

    record Button(long x, long y) {
        public static Button fromString(String b) {
            List<Long> moves = Arrays.stream(b.split(": ")[1].split(", "))
                    .map(m -> Long.parseLong(m.split("\\+")[1]))
                    .toList();
            return new Button(moves.get(0), moves.get(1));
        }
    }
    record Prize(long x, long y) {
        public static Prize fromString(String p) {
            List<Long> dest = Arrays.stream(p.split(": ")[1].split(", "))
                    .map(d -> Long.parseLong(d.split("=")[1]))
                    .toList();
            return new Prize(dest.get(0), dest.get(1));
        }
    }
    record Machine(Button a, Button b, Prize p) {
        public Machine increasePrize() {
            return new Machine(a, b, new Prize(p.x() + 10000000000000L, p.y() + 10000000000000L));
        }
    }
    record Solution(long moveA, long moveB) {}


    @Override
    public Integer firstStar() {
        List<Machine> machines = this.getInput(Day13::parseMachines);

        return machines.stream()
                .map(this::solve)
                .filter(Objects::nonNull)
                .mapToInt(s -> Math.toIntExact(s.moveA() * 3 + s.moveB()))
                .sum();
    }

    @Override
    public Long secondStar() {
        List<Machine> machines = this.getInput(Day13::parseMachines);

        return machines.stream()
                .map(Machine::increasePrize)
                .map(this::solve)
                .filter(Objects::nonNull)
                .mapToLong(s -> s.moveA() * 3 + s.moveB())
                .sum();
    }

    private Solution solve(Machine m) {
        // solve Parametrized equation : a*ButtonA + b*ButtonB = Prize
        long xa = m.a().x();
        long ya = m.a().y();
        long xb = m.b().x();
        long yb = m.b().y();
        long px = m.p().x();
        long py = m.p().y();

        long a = (px * yb - py * xb) / (xa * yb - ya * xb);
        long b = (px * ya - py * xa) / (xb * ya - yb * xa);

        // check solution
        if (a * xa + b * xb == px && a * ya + b * yb == py) {
            return new Solution(a, b);
        }
        return null;
    }


    private static List<Machine> parseMachines(List<String> input) {
        Iterator<String> lines = input.iterator();

        List<Machine> machines = new ArrayList<>();
        while (lines.hasNext()) {
            machines.add(new Machine(
                    Button.fromString(lines.next()),
                    Button.fromString(lines.next()),
                    Prize.fromString(lines.next())
            ));
            if (lines.hasNext()) {
                lines.next();
            }
        }

        return machines;
    }
}
