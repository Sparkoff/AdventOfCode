package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day22 extends DayBase<List<Day22.Instruction>, Long , Long> {

    public Day22() {
        super();
    }

    public Day22(List<String> input) {
        super(input);
    }

    record Cuboid(int xmin, int xmax, int ymin, int ymax, int zmin, int zmax) {
        public boolean isSmallRegion() {
            return xmin >= -50 && xmax <= 50 &&
                    ymin >= -50 && ymax <= 50 &&
                    zmin >= -50 && zmax <= 50;
        }

        public long getVolume() {
            return (long) (xmax - xmin + 1) * (ymax - ymin + 1) * (zmax - zmin + 1);
        }

        public static Cuboid getIntersection(Cuboid c1, Cuboid c2) {
            Cuboid i = new Cuboid(
                    Math.max(c1.xmin, c2.xmin), Math.min(c1.xmax, c2.xmax),
                    Math.max(c1.ymin, c2.ymin), Math.min(c1.ymax, c2.ymax),
                    Math.max(c1.zmin, c2.zmin), Math.min(c1.zmax, c2.zmax)
            );
            return (i.xmin > i.xmax || i.ymin > i.ymax || i.zmin > i.zmax) ? null : i;
        }
    }
    record Instruction(boolean on, Cuboid cuboid) {}

    @Override
    public Long firstStar() {
        List<Instruction> procedure = this.getInput(Day22::parseStartingPositions);

        return computeProcedure(procedure, true);
    }

    @Override
    public Long secondStar() {
        List<Instruction> procedure = this.getInput(Day22::parseStartingPositions);

        return computeProcedure(procedure, false);
    }

    private static Long computeProcedure(List<Instruction> procedure, boolean smallRegion) {
        if (smallRegion) {
            procedure = procedure.stream()
                    .filter(i -> i.cuboid().isSmallRegion())
                    .toList();
        }

        List<Instruction> detailedProcedure = new ArrayList<>();

        for (Instruction i : procedure) {
            List<Instruction> nextDetailedProcedure = new ArrayList<>();
            Cuboid nextCuboid = i.cuboid();

            if (i.on()) {
                nextDetailedProcedure.add(i);
            }

            for (Instruction dp : detailedProcedure) {
                Cuboid intersection = Cuboid.getIntersection(dp.cuboid(), nextCuboid);
                if (intersection != null) {
                    nextDetailedProcedure.add(new Instruction(!dp.on(), intersection));
                }
            }

            detailedProcedure.addAll(nextDetailedProcedure);
        }

        return detailedProcedure.stream()
                .map(i -> (i.on() ? 1 : -1) * i.cuboid().getVolume())
                .mapToLong(Long::longValue)
                .sum();
    }

    private static List<Instruction> parseStartingPositions(List<String> input) {
        Pattern p = Pattern.compile("(on|off) x=(-?\\d+)\\.\\.(-?\\d+),y=(-?\\d+)\\.\\.(-?\\d+),z=(-?\\d+)\\.\\.(-?\\d+)");

        return input.stream()
                .map(l -> {
                    Matcher m = p.matcher(l);
                    if (!m.find()) throw new RuntimeException("Regex not matching for : " + l);
                    return m;
                })
                .map(m -> new Instruction(
                        m.group(1).equals("on"),
                        new Cuboid(
                                Integer.parseInt(m.group(2)),
                                Integer.parseInt(m.group(3)),
                                Integer.parseInt(m.group(4)),
                                Integer.parseInt(m.group(5)),
                                Integer.parseInt(m.group(6)),
                                Integer.parseInt(m.group(7))
                        )
                ))
                .toList();
    }
}