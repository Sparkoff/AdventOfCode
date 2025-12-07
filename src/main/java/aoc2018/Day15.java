package aoc2018;

import common.DayBase;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day15 extends DayBase<Day15.Cavern, Integer, Integer> {

    public Day15() {
        super();
    }

    public Day15(List<String> input) {
        super(input);
    }

    record Cave(List<Point> walls, int width, int height) {
        public boolean inMap(Point p) {
            return p.x() >= 0 && p.x() < width &&
                    p.y() >= 0 && p.y() < height;
        }
    }
    record Point(int x, int y) implements Comparable<Point> {
        public List<Point> getAdjacentPoints() {
            return List.of(
                    new Point(x, y - 1),
                    new Point(x - 1, y),
                    new Point(x + 1, y),
                    new Point(x, y + 1)
            );
        }

        @Override
        public int compareTo(Point other) {
            return Comparator.comparingInt(Point::y)
                    .thenComparingInt(Point::x)
                    .compare(this, other);
        }
    }

    enum PlayerType { ELF, GOBLIN }
    static class Player {
        private int hp = 200;
        private int dps = 3;
        private Point location;
        private final PlayerType type;

        public Player(int x, int y, PlayerType type) {
            location = new Point(x, y);
            this.type = type;
        }

        public Player(Player original) {
            this.hp = original.hp;
            this.dps = original.dps;
            this.location = original.location;
            this.type = original.type;
        }

        public Point getLocation() {
            return location;
        }
        public void setLocation(Point location) {
            this.location = location;
        }

        public List<Point> getRange() {
            return location.getAdjacentPoints();
        }

        public void attack(Player opponent) {
            opponent.hp -= dps;
            if (opponent.hp < 0) {
                opponent.hp = 0;
            }
        }
        public void setDPS(int dps) {
            this.dps = dps;
        }

        public int getHP() {
            return hp;
        }

        public boolean isDead() {
            return hp == 0;
        }

        public PlayerType getType() {
            return type;
        }

        public int compareByLocation(Player other) {
            return location.compareTo(other.location);
        }

        public int compareByHp(Player other) {
            return Comparator.comparingInt(Player::getHP)
                    .compare(this, other);
        }

        public List<Point> getOpponentRanges(List<Player> alivePlayers) {
            return alivePlayers.stream()
                    .filter(p -> p.type != type)
                    .map(Player::getRange)
                    .flatMap(List::stream)
                    .distinct()
                    .toList();
        }

        public List<Point> filterTargetsInRange(List<Point> targets) {
            return targets.stream()
                    .filter(t -> t.equals(location))
                    .toList();
        }

        public Optional<Player> findOpponent(List<Player> alivePlayers) {
            List<Point> playerRange = getRange();
            return alivePlayers.stream()
                    .filter(p -> p.type != type)
                    .filter(p -> playerRange.contains(p.location))
                    .min(Player::compareByHp);
        }
    }

    record Cavern(Cave cave, List<Player> players) {
        @Override
        public List<Player> players() {
            // make a copy to not alter the original during a run
            return players.stream()
                    .map(Player::new)
                    .collect(Collectors.toList());
        }
    }

    record Path(Point firstStep, Point target, int distance) {}


    @Override
    public Integer firstStar() {
        Cavern cavern = this.getInput(Day15::parseCavern);

        List<Player> players = cavern.players();

        int round = run(cavern.cave(), players, false);
        return round *
                players.stream()
                        .mapToInt(Player::getHP)
                        .sum();
    }

    @Override
    public Integer secondStar() {
        Cavern cavern = this.getInput(Day15::parseCavern);

        Map<Integer,Integer> scores = new HashMap<>();

        // *2 until victory, then half difference of min-max dps
        //   dps   outcome                   lower  upper
        //     4   elves won with losses         4    inf
        //     8   elves won with losses         8    inf
        //    16   elves won with losses        16    inf
        //    32   elves flawless victory       16     32
        //    24   elves won with losses        24     48
        //    28   elves flawless victory       24     28
        //    26   elves flawless victory       24     26
        //    25   elves flawless victory       24     25
        // dps 25: elf victory with remaining hit points 1376 on round 34
        int dps = 1;
        int dps_min = 4;
        int dps_max = Integer.MAX_VALUE;
        while (true) {
            if (dps == 1) {
                dps = dps_min;
            } else if (dps_max == Integer.MAX_VALUE) {
                dps = dps * 2;
            } else if (dps_max - dps_min > 1) {
                dps = dps_min + (dps_max - dps_min) / 2;
            } else {
                return scores.get(dps_max);
            }

            List<Player> players = cavern.players();
            for (Player player : players) {
                // increase Attack Power of Elves
                if (player.getType() == PlayerType.ELF) {
                    player.setDPS(dps);
                }
            }

            int round = run(cavern.cave(), players, true);

            if (round != -1) {
                int hp = players.stream()
                        .mapToInt(Player::getHP)
                        .sum();
                scores.put(
                        dps,
                        round * hp
                );
                dps_max = dps;
            } else {
                dps_min = dps;
            }
        }
    }

    private static int run(Cave cave, List<Player> players, boolean noElfLoss) {
        int round = 0;
        mainLoop: while (players.stream().collect(Collectors.groupingBy(Player::getType)).size() == 2) {
            players.sort(Player::compareByLocation);

            for (Player player : players) {
                if (player.isDead()) continue;

                List<Player> alivePlayers = players.stream()
                        .filter(p -> !p.isDead())
                        .toList();

                // prepare move
                List<Point> targets = player.getOpponentRanges(alivePlayers);

                // end game if player has no target, no round increment
                if (targets.isEmpty()) break mainLoop;

                List<Point> targetsInRange = player.filterTargetsInRange(targets);

                // move current player if it is not already close to an opponent
                if (targetsInRange.isEmpty()) {
                    List<Point> nonFreeSquares = Stream.concat(
                            cave.walls().stream(),
                            alivePlayers.stream().map(Player::getLocation)
                    ).toList();

                    movePlayer(player, targets, cave, nonFreeSquares);
                }

                // fight
                Optional<Player> opponent = player.findOpponent(alivePlayers);

                if (opponent.isPresent()) {
                    player.attack(opponent.get());

                    // elves died during the battle, this is a failure
                    if (noElfLoss &&
                            opponent.get().getType() == PlayerType.ELF &&
                            opponent.get().isDead()) {
                        return -1;
                    }
                }
            }

            // remove dead
            players = players.stream()
                    .filter(p -> !p.isDead())
                    .collect(Collectors.toList());

            round++;
        }

        return round;
    }

    private static void movePlayer(Player player, List<Point> targets, Cave cave, List<Point> nonFreeSquares) {
        findBestMove(player, targets, cave, nonFreeSquares).ifPresent(player::setLocation);
    }

    private static Optional<Point> findBestMove(Player player, List<Point> targets, Cave cave, List<Point> nonFreeSquares) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.add(player.getLocation());

        // This map tracks visited points and the first step taken from the player's location to reach them.
        Map<Point, Point> firstStepMap = new HashMap<>();
        firstStepMap.put(player.getLocation(), player.getLocation()); // The path to the start is the start itself.

        List<Path> solutions = new ArrayList<>();

        while (!queue.isEmpty()) {
            // Perform a level-by-level BFS.
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Point current = queue.poll();

                if (current != null) {
                    for (Point neighbor : current.getAdjacentPoints()) {
                        if (!cave.inMap(neighbor) || nonFreeSquares.contains(neighbor) || firstStepMap.containsKey(neighbor)) {
                            continue;
                        }

                        // Determine the first step taken from the player's location.
                        Point step = current.equals(player.getLocation()) ? neighbor : firstStepMap.get(current);
                        firstStepMap.put(neighbor, step);

                        if (targets.contains(neighbor)) {
                            // We found a path to a target. The distance is implicit by the BFS level.
                            solutions.add(new Path(step, neighbor, 0)); // Distance value is not needed for comparison here.
                        } else {
                            queue.add(neighbor);
                        }
                    }
                }
            }

            // If we have found any solutions at this level (shortest distance), we can stop searching.
            if (!solutions.isEmpty()) {
                break;
            }
        }

        return solutions.stream()
                .min(Comparator.comparing(Path::target)
                        .thenComparing(Path::firstStep))
                .map(Path::firstStep);
    }

    private static Cavern parseCavern(List<String> input) {
        List<Point> walls = new ArrayList<>();
        List<Player> players = new ArrayList<>();

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                char c = input.get(y).charAt(x);
                switch (c) {
                    case '#':
                        walls.add(new Point(x, y));
                        break;

                    case 'E':
                    case 'G':
                        players.add(new Player(x, y, c == 'E' ? PlayerType.ELF : PlayerType.GOBLIN));
                        break;

                    default:
                        if (c != '.') {
                            throw new IllegalStateException("Unexpected cavern char : " + c);
                        }
                }

            }
        }

        return new Cavern(
                new Cave(walls, input.get(0).length(), input.size()),
                players
        );
    }
}
