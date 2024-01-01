package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day20 extends DayBase<List<Day20.Particle>, Integer, Integer> {

    public Day20() {
        super();
    }

    public Day20(List<String> input) {
        super(input);
    }

    record Point(int x, int y, int z) {
        public int manhattan() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }
    }
    record Particle(int id, Point p, Point v, Point a) {
        public int manhattanAt(int time) {
            return locationAt(time).manhattan();
        }
        public Point locationAt(int time) {
            return new Point(
                    p.x + (time * v.x) + ((time * (time + 1) * a.x) / 2),
                    p.y + (time * v.y) + ((time * (time + 1) * a.y) / 2),
                    p.z + (time * v.z) + ((time * (time + 1) * a.z) / 2)
            );
        }
    }
    record Collision(int time, int p1, int p2) {
        public List<Integer> particles() {
            return List.of(p1, p2);
        }
    }

    @Override
    public Integer firstStar() {
        List<Particle> swarm = this.getInput(Day20::parseSwarm);

        return swarm.stream()
                .min(Comparator.comparingInt(p -> p.manhattanAt((int) 1E3)))
                .orElseThrow()
                .id();
    }

    @Override
    public Integer secondStar() {
        List<Particle> swarm = this.getInput(Day20::parseSwarm);

        List<List<Collision>> collisionsGroupByTime = exploreCollisions(swarm).stream()
                .collect(Collectors.groupingBy(Collision::time))
                .entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .toList();

        // use set for performance in containsAll()
        Set<Integer> remainingParticles = swarm.stream()
                .map(Particle::id)
                .collect(Collectors.toSet());

        for (List<Collision> collisions : collisionsGroupByTime) {
            // use set for automated distinct on insert
            Set<Integer> destroyed = new HashSet<>();
            for (Collision collision : collisions) {
                // collision only occurs if particle still exist in Swarm
                if (remainingParticles.containsAll(collision.particles())) {
                    destroyed.addAll(collision.particles());
                }
            }

            remainingParticles = remainingParticles.stream()
                    .filter(id -> !destroyed.contains(id))
                    .collect(Collectors.toSet());
        }

        return remainingParticles.size();
    }

    private static List<Collision> exploreCollisions(List<Particle> swarm) {
        List<Collision> collisions = new ArrayList<>();
        for (int i = 0; i < swarm.size(); i++) {
            for (int j = i + 1; j < swarm.size(); j++) {
                int t = checkCollision(swarm.get(i), swarm.get(j));
                if (t >= 0) {
                    collisions.add(new Collision(t, swarm.get(i).id(), swarm.get(j).id()));
                }
            }
        }
        return collisions;
    }
    private static int checkCollision(Particle p1, Particle p2) {
        List<Integer> tx = solve(p1.a.x(), p2.a.x(), p1.v.x(), p2.v.x(), p1.p.x(), p2.p.x());
        List<Integer> ty = solve(p1.a.y(), p2.a.y(), p1.v.y(), p2.v.y(), p1.p.y(), p2.p.y());
        List<Integer> tz = solve(p1.a.z(), p2.a.z(), p1.v.z(), p2.v.z(), p1.p.z(), p2.p.z());

        // concat and test all solutions from axis
        // select the lowest answer that works
        return Stream.of(tx, ty, tz)
                .flatMap(List::stream)
                .distinct()
                .filter(t -> p1.locationAt(t).equals(p2.locationAt(t)))
                .min(Integer::compareTo)
                .orElse(-1);
    }
    private static List<Integer> solve(int a1, int a2, int v1, int v2, int p1, int p2) {
        List<Integer> solutions = new ArrayList<>();

        // second degree equation : (A0/2)t2 + (V0 + A0/2)t + P0 = at2 + bt + c = 0
        // do not care about float division cast to int:
        // any solution found on axis will be verified on particle level later
        double a = (double) (a1 - a2) / 2;
        double b = v1 - v2 + a;
        double c = p1 - p2;

        if (a == 0) {
            // if a == 0, we can't resolve it as second deg eq (dividing by 0!)
            if (b != 0) {
                // if b or c == 0, there would be no or any solution (provided by other axis, if any)
                solutions.add((int) (-c / b));
            }
        } else {
            double delta = Math.pow(b, 2) - 4 * a * c;
            if (delta >= 0) {
                // if b == 0, solutions would be +/-sqrt(-c/a), as expected
                solutions.add((int) (-(b + Math.sqrt(delta)) / (2 * a)));
                solutions.add((int) (-(b - Math.sqrt(delta)) / (2 * a)));
            }
        }

        // only keep positive solutions as we are working on running time
        return solutions.stream()
                .filter(s -> s >= 0)
                .toList();
    }

    private static List<Particle> parseSwarm(List<String> input) {
        List<Particle> swarm = new ArrayList<>();
        int id = 0;

        Pattern pattern = Pattern.compile("(-?\\d+)");
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);

            List<Integer> ints = new ArrayList<>();
            while (matcher.find()) {
                ints.add(Integer.parseInt(matcher.group()));
            }

            swarm.add(new Particle(
                    id++,
                    new Point(ints.get(0), ints.get(1), ints.get(2)),
                    new Point(ints.get(3), ints.get(4), ints.get(5)),
                    new Point(ints.get(6), ints.get(7), ints.get(8))
            ));
        }

        return swarm;
    }
}
