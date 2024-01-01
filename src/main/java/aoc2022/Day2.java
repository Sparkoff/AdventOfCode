package aoc2022;

import common.DayBase;

import java.util.List;


public class Day2 extends DayBase<List<Day2.Prediction>, Integer, Integer> {

    public Day2() {
        super();
    }

    public Day2(List<String> input) {
        super(input);
    }


    enum Hand {
        ROCK(1), PAPER(2), SCISSORS(3);

        public final int value;
        Hand(int value) {
            this.value = value;
        }

        public static Hand rawHand(String h) {
            return switch (h) {
                case "A", "X" -> ROCK;
                case "B", "Y" -> PAPER;
                case "C", "Z" -> SCISSORS;
                default -> throw new IllegalArgumentException();
            };
        }
    }

    record Prediction(String p1, String p2) {}


    @Override
    public Integer firstStar() {
        List<Prediction> strategy = this.getInput(Day2::parseStrategy);
        return strategy.stream()
                .map(Day2::rawScore)
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<Prediction> strategy = this.getInput(Day2::parseStrategy);
        return strategy.stream()
                .map(Day2::indicatedScore)
                .mapToInt(Integer::intValue)
                .sum();
    }


    private static int rawScore(Prediction p) {
        Hand h1 = Hand.rawHand(p.p1());
        Hand h2 = Hand.rawHand(p.p2());
        if (h1.equals(h2)) {
            return 3 + h2.value;
        } else if ((h1.value + 1) % 3 == h2.value % 3) {
            return 6 + h2.value;
        } else {
            return h2.value;
        }
    }
    private static int indicatedScore(Prediction p) {
        Hand h1 = Hand.rawHand(p.p1());
        return switch (p.p2()) {
            case "X" -> ((h1.value + 1) % 3) + 1;
            case "Y" -> 3 + h1.value;
            case "Z" -> 6 + (h1.value % 3) + 1;
            default -> throw new IllegalArgumentException();
        };
    }


    private static List<Prediction> parseStrategy(List<String> input) {
        return input.stream()
                .map(l -> l.split(" "))
                .map(l -> new Prediction(l[0], l[1]))
                .toList();
    }
}
