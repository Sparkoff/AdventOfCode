package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day16 extends DayBase<List<Day16.DanceMove>, String, String> {

    public Day16() {
        super();
    }

    public Day16(List<String> input) {
        super(input);
    }

    enum MoveType {SPIN, EXCHANGE, PARTNER}
    record DanceMove(MoveType type, int spinSize, String a, int idxA, String b, int idxB) {
        public DanceMove(int spinSize) {
            this(MoveType.SPIN, spinSize, null, 0, null, 0);
        }
        public DanceMove(int a, int b) {
            this(MoveType.EXCHANGE, 0, null, a, null, b);
        }
        public DanceMove(String a, String b) {
            this(MoveType.PARTNER, 0, a, 0, b, 0);
        }

        public String apply(String dancers) {
            StringBuilder dancersBuffer = new StringBuilder(dancers);
            switch (type) {
                case SPIN:
                    dancers = dancers.substring(dancers.length() - spinSize) + dancers.substring(0, dancers.length() - spinSize);
                    break;
                case EXCHANGE:
                    char dancerA = dancers.charAt(idxA);
                    char dancerB = dancers.charAt(idxB);
                    dancersBuffer.setCharAt(idxA, dancerB);
                    dancersBuffer.setCharAt(idxB, dancerA);
                    dancers = String.valueOf(dancersBuffer);
                    break;
                case PARTNER:
                    int idxDancerA = dancers.indexOf(a);
                    int idxDancerB = dancers.indexOf(b);
                    dancersBuffer.setCharAt(idxDancerA, b.charAt(0));
                    dancersBuffer.setCharAt(idxDancerB, a.charAt(0));
                    dancers = String.valueOf(dancersBuffer);
                    break;
            }
            return dancers;
        }
    }

    @Override
    public String firstStar() {
        List<DanceMove> moves = this.getInput(Day16::parseDance);

        String dancers = "abcdefghijklmnop";
        if (moves.size() == 3) {
            dancers = "abcde";
        }

        for (DanceMove move : moves) {
            dancers = move.apply(dancers);
        }

        return dancers;
    }

    @Override
    public String secondStar() {
        List<DanceMove> moves = this.getInput(Day16::parseDance);

        String dancers = "abcdefghijklmnop";
        int danceLenth = (int) 1E9;
        if (moves.size() == 3) {
            dancers = "abcde";
            danceLenth = 2;
        }

        List<String> steps = new ArrayList<>();
        steps.add(dancers);
        for (long i = 0; i < danceLenth; i++) {
            for (DanceMove move : moves) {
                dancers = move.apply(dancers);
            }

            if (dancers.equals(steps.get(0))) {
                // a routine is discovered, no need to dance further
                // final standing can be guessed
                return steps.get(danceLenth % steps.size());
            } else {
                steps.add(dancers);
            }
        }

        return dancers;
    }

    private static List<DanceMove> parseDance(List<String> input) {
        return Arrays.stream(input.get(0).split(","))
                .map(Day16::parseMove)
                .toList();
    }
    private static DanceMove parseMove(String move) {
        return switch (move.charAt(0)) {
            case 's' -> new DanceMove(Integer.parseInt(move.substring(1)));
            case 'x' -> {
                String[] indexes = move.substring(1).split("/");
                yield new DanceMove(
                        Integer.parseInt(indexes[0]),
                        Integer.parseInt(indexes[1])
                );
            }
            case 'p' -> {
                String[] dancers = move.substring(1).split("/");
                yield new DanceMove(dancers[0], dancers[1]);
            }
            default -> throw new IllegalArgumentException("Can't parse move : " + move);
        };

    }
}
