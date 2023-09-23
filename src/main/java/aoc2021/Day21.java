package aoc2021;

import common.DayBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day21 extends DayBase<Day21.InitPawn, Long, Long> {

    public Day21() {
        super();
    }

    public Day21(List<String> input) {
        super(input);
    }

    record InitPawn(int player1, int player2) {}

    record Pawn(int position, int score) {}
    record QuantumDice(int value, long frequency) {}

    private static final List<QuantumDice> QUANTUM_DICES = List.of(
            new QuantumDice(3, 1L),
            new QuantumDice(4, 3L),
            new QuantumDice(5, 6L),
            new QuantumDice(6, 7L),
            new QuantumDice(7, 6L),
            new QuantumDice(8, 3L),
            new QuantumDice(9, 1L)
    );

    static class VictorySteps {
        private Map<Integer, Long> victories = new HashMap<>();
        private Map<Integer, Long> fails = new HashMap<>();

        public VictorySteps(Map<Integer, Long> victories, Map<Integer, Long> fails) {
            this.victories = victories;
            this.fails = fails;
        }
        public VictorySteps() {}

        public void addVictory(int round, long frequency) {
            victories.put(round, victories.getOrDefault(round, 0L) + frequency);
        }
        public void addFail(int round, long frequency) {
            fails.put(round, fails.getOrDefault(round, 0L) + frequency);
        }

        public static VictorySteps merge(VictorySteps s1, VictorySteps s2) {
            return new VictorySteps(
                    Stream.of(s1.victories, s2.victories)
                            .flatMap(map -> map.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum)),
                    Stream.of(s1.fails, s2.fails)
                            .flatMap(map -> map.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum))
            );
        }
    }

    @Override
    public Long firstStar() {
        InitPawn init = this.getInput(Day21::parseStartingPositions);

        int round = 0;
        int dice = 0;
        Pawn ply1 = new Pawn(init.player1(), 0);
        Pawn ply2 = new Pawn(init.player2(), 0);
        while (ply1.score() < 1000 && ply2.score() < 1000) {
            round++;

            int move = 0;
            for (int i = 0; i < 3; i++) {
                dice = (++dice) % 100;
                move += (dice == 0) ? 100 : dice;
            }

            List<Pawn> pawns = movePawn(ply1, ply2, round % 2 != 0, move);
            ply1 = pawns.get(0);
            ply2 = pawns.get(1);
        }

        return (long) round * 3 * (ply1.score() >= 1000 ? ply2.score() : ply1.score());
    }

    @Override
    public Long secondStar() {
        InitPawn init = this.getInput(Day21::parseStartingPositions);

        return getMaxVictories(
                computeVictories(init.player1()),
                computeVictories(init.player2())
        );
    }

    private static List<Pawn> movePawn(Pawn ply1, Pawn ply2, boolean ply1Turn, int move) {
        if (ply1Turn) {
            return List.of(computePawn(ply1, move), ply2);
        } else {
            return List.of(ply1, computePawn(ply2, move));
        }
    }

    private static Pawn computePawn(Pawn p, int move) {
        int pos = (p.position() + move) % 10;
        int score = p.score() + ((pos == 0) ? 10 : pos);
        return new Pawn(pos, score);
    }

    private static VictorySteps computeVictories(int startPosition) {
        return computeVictories(new Pawn(startPosition, 0), 1, 1L);
    }
    private static VictorySteps computeVictories(Pawn pawn, int round, long universeCount) {
        VictorySteps steps = new VictorySteps();
        for (QuantumDice dice : QUANTUM_DICES) {
            Pawn next = computePawn(pawn, dice.value());
            long frequency = universeCount * dice.frequency();
            if (next.score() >= 21) {
                steps.addVictory(round, frequency);
            } else {
                steps.addFail(round, frequency);
                steps = VictorySteps.merge(steps, computeVictories(next, round + 1, frequency));
            }
        }
        return steps;
    }

    private static long getMaxVictories(VictorySteps ply1, VictorySteps ply2) {
        long ply1VictoryCount = ply1.victories.entrySet().stream()
                .map(e1 -> e1.getValue() * (Long) ply2.fails.entrySet().stream()
                        .filter(e2 -> e2.getKey().equals(e1.getKey() - 1))  // if both player have victories on same round, ply1 win as he plays first
                        .map(Map.Entry::getValue)
                        .mapToLong(Long::longValue)
                        .sum())
                .mapToLong(Long::longValue)
                .sum();
        long ply2VictoryCount = ply2.victories.entrySet().stream()
                .map(e2 -> e2.getValue() * (Long) ply1.fails.entrySet().stream()
                        .filter(e1 -> e1.getKey().equals(e2.getKey()))  // if both player have victories on same round, ply2 fails as he plays second
                        .map(Map.Entry::getValue)
                        .mapToLong(Long::longValue)
                        .sum())
                .mapToLong(Long::longValue)
                .sum();

        return Math.max(ply1VictoryCount, ply2VictoryCount);
    }

    private static InitPawn parseStartingPositions(List<String> input) {
        return new InitPawn(
                Integer.parseInt(input.get(0).split(": ")[1]),
                Integer.parseInt(input.get(1).split(": ")[1])
        );
    }
}